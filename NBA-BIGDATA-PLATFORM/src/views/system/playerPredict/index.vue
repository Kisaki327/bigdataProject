<template>
  <div class="app-container">
    <div class="control-bar">
      <div class="search-box">
        <el-input
          v-model="searchQuery"
          placeholder="搜索球员姓名..."
          clearable
          @input="searchPlayer"
          @focus="searchPlayer"
        ></el-input>
        <div v-if="showPlayerList" class="player-list-grid">
          <div v-if="filteredPlayers.length === 0" class="player-item-grid">
            未找到匹配球员
          </div>
          <div
            v-for="player in filteredPlayers"
            :key="player.playerid"
            class="player-item-grid"
            @click="selectPlayer(player)"
          >
            <img :src="player.avatar" alt="头像" class="player-avatar" />
            <div class="player-name">{{ player.name }}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="player-info" v-if="currentPlayer">
      <div class="player-header">
        <img :src="currentPlayer.avatar" class="avatar" alt="头像" />
        <div class="info-text">
          <div class="name-cn">{{ currentPlayer.firstName }} {{ currentPlayer.lastName }}</div>
          <div class="name-en">{{ currentPlayer.firstNameEn }} {{ currentPlayer.lastNameEn }}</div>
          <div class="team">
            <img :src="currentPlayer.logo" class="team-logo" alt="logo" />
            {{ currentPlayer.teamname }}
          </div>
        </div>
      </div>
    </div>
    <div ref="chartContainer" class="chart-container"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { debounce } from 'lodash'
import { PlayerGames } from '@/api/system/players'

export default {
  name: 'NbaPrediction',
  data() {
    return {
      searchQuery: '',
      showPlayerList: false,
      currentPlayer: null,
      myChart: null,
      playerDatabase: []
    }
  },
  computed: {
    filteredPlayers() {
      if (!this.searchQuery) return []
      const query = this.searchQuery.toLowerCase()
      return this.playerDatabase.filter(player =>
        player.name.toLowerCase().includes(query) ||
        (player.teamname && player.teamname.toLowerCase().includes(query))
      )
    }
  },
  mounted() {
    this.initChart()
    document.addEventListener('click', this.handleDocumentClick)
    this.fetchData()
  },
  beforeDestroy() {
    if (this.myChart) this.myChart.dispose()
    document.removeEventListener('click', this.handleDocumentClick)
    this.$store.dispatch('chart/clearCharts')
  },
  methods: {
    async fetchData() {
      const res = await PlayerGames()
      const grouped = {}
      res.data.forEach(item => {
        const pid = item.playerid || ('unknown_' + item.firstName + item.lastName)
        if (!grouped[pid]) {
          grouped[pid] = {
            playerid: pid,
            firstName: item.firstName,
            lastName: item.lastName,
            firstNameEn: item.firstNameEn,
            lastNameEn: item.lastNameEn,
            avatar: item.avatar,
            teamname: item.teamname,
            logo: item.logo,
            // 先空数组，真实数据
            score: [],
            assist: [],
            rebound: [],
            threeAttempts: [],
            threePercentage: [],
            fga: [],
            mins: [],
            steals: [],
            fgpctStr: [],
            // 预测数据单独存，后面插入
            predScore: null,
            predAssist: null,
            predRebound: null,
            predThreeAttempts: null,
            predThreePercentage: null,
            predFga: null,
            predMins: null,
            predSteals: null,
            predFgpctStr: null,
          }
        }
        if (item.dataType === 0) {
          // 预测数据，赋值
          grouped[pid].predScore = item.points
          grouped[pid].predAssist = item.assists
          grouped[pid].predRebound = item.rebs
          grouped[pid].predThreeAttempts = item.tpa
          grouped[pid].predThreePercentage = item.tppctStr
          grouped[pid].predFga = item.fga
          grouped[pid].predMins = item.mins
          grouped[pid].predSteals = item.steals
          grouped[pid].predFgpctStr = item.fgpctStr
        } else {
          grouped[pid].score.push(item.points)
          grouped[pid].assist.push(item.assists)
          grouped[pid].rebound.push(item.rebs)
          grouped[pid].threeAttempts.push(item.tpa)
          grouped[pid].threePercentage.push(item.tppctStr)
          grouped[pid].fga.push(item.fga)
          grouped[pid].mins.push(item.mins)
          grouped[pid].steals.push(item.steals)
          grouped[pid].fgpctStr.push(item.fgpctStr)
        }
      })
      this.playerDatabase = Object.values(grouped).map(p => {
        // 把预测数据push到数组最后
        if (p.predScore !== null) p.score.push(p.predScore)
        if (p.predAssist !== null) p.assist.push(p.predAssist)
        if (p.predRebound !== null) p.rebound.push(p.predRebound)
        if (p.predThreeAttempts !== null) p.threeAttempts.push(p.predThreeAttempts)
        if (p.predThreePercentage !== null) p.threePercentage.push(p.predThreePercentage)
        if (p.predFga !== null) p.fga.push(p.predFga)
        if (p.predMins !== null) p.mins.push(p.predMins)
        if (p.predSteals !== null) p.steals.push(p.predSteals)
        if (p.predFgpctStr !== null) p.fgpctStr.push(p.predFgpctStr)

        return {
          ...p,
          name: `${p.firstName} ${p.lastName}`,
          data: {
            score: p.score,
            assist: p.assist,
            rebound: p.rebound,
            threeAttempts: p.threeAttempts,
            threePercentage: p.threePercentage,
            fga: p.fga,
            mins: p.mins,
            steals: p.steals,
            fgpctStr: p.fgpctStr
          }
        }
      })
      this.currentPlayer = Object.values(this.playerDatabase)[0]
      this.updateCharts(this.currentPlayer)
    },
    initChart() {
      this.myChart = echarts.init(this.$refs.chartContainer)
    },
    prepareSeriesData(data, color) {
      return data.map((value, index) => ({
        value: value,
        itemStyle: {
          color: index === data.length - 1 ? '#FF0000' : color,
          borderWidth: index === data.length - 1 ? 3 : 1,
          borderColor: index === data.length - 1 ? '#000' : color
        },
        symbol: index === data.length - 1 ? 'diamond' : 'circle',
        symbolSize: index === data.length - 1 ? 10 : 6
      }))
    },
    updateCharts(player) {
      if (!player || !player.data) {
        this.myChart.clear()
        return
      }
      const playerData = player.data

      const realCount = playerData.score.length - 1
      const xData = []
      for (let i = 1; i <= realCount; i++) xData.push(String(i))
      xData.push('预测')

      const option = {
        title: {
          text: `${player.name} (${player.teamname}) - 数据趋势`,
          subtext: '红色菱形标记为预测数据',
          subtextStyle: { color: '#FF0000', fontWeight: 'bold' }
        },
        tooltip: {
          trigger: 'axis',
          formatter(params) {
            let result = `第${params[0].axisValue}场:`
            params.forEach(param => {
              const isPrediction = param.dataIndex === params[0].data.length - 1
              const suffix = isPrediction ? ' (预测)' : ''
              result += `<br/>${param.seriesName}: ${param.value}${suffix}`
            })
            return result
          }
        },
        legend: { data: ['得分', '助攻', '篮板', '三分出手', '三分命中率', '出手次数', '上场时间', '抢断', '投篮命中率'] },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        toolbox: { feature: { saveAsImage: {} } },
        xAxis: { type: 'category', boundaryGap: false, data: xData },
        yAxis: { type: 'value' },
        series: [
          { name: '得分', type: 'line', data: this.prepareSeriesData(playerData.score, '#5470C6') },
          { name: '助攻', type: 'line', data: this.prepareSeriesData(playerData.assist, '#91CC75') },
          { name: '篮板', type: 'line', data: this.prepareSeriesData(playerData.rebound, '#EE6666') },
          { name: '三分出手', type: 'line', data: this.prepareSeriesData(playerData.threeAttempts, '#FFD700') },
          { name: '三分命中率', type: 'line', data: this.prepareSeriesData(playerData.threePercentage, '#9C27B0') },
          { name: '出手次数', type: 'line', data: this.prepareSeriesData(playerData.fga, '#00CED1') },
          { name: '上场时间', type: 'line', data: this.prepareSeriesData(playerData.mins, '#FF69B4') },
          { name: '抢断', type: 'line', data: this.prepareSeriesData(playerData.steals, '#8B4513') },
          { name: '投篮命中率', type: 'line', data: this.prepareSeriesData(playerData.fgpctStr, '#7B68EE') },
        ]
      }
      this.myChart.setOption(option)
      this.$store.dispatch('chart/setChart', {
        id:option.title.text,
        option: option,
        type:'line'
      })
      window.addEventListener('resize', () => {
        this.myChart.resize()
      })
    },
    searchPlayer: debounce(function () {
      if (!this.searchQuery) {
        this.currentPlayer = null
        this.myChart.clear()
        this.showPlayerList = false
        return
      }
      if (this.filteredPlayers.length === 1) {
        this.selectPlayer(this.filteredPlayers[0])
        this.showPlayerList = false
      } else {
        this.showPlayerList = true
      }
    }, 100),
    selectPlayer(player) {
      this.currentPlayer = player
      this.searchQuery = player.name
      this.updateCharts(player)
      this.showPlayerList = false
    },
    handleDocumentClick(e) {
      if (!this.$el.contains(e.target)) {
        this.showPlayerList = false
      }
    },

  }
}
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 84px);
  padding: 20px;
  box-sizing: border-box;
}
.control-bar {
  flex: 0 0 auto;
  position: absolute;
  top: 20px;
  left: 20px;
  right: 20px;
  z-index: 100;
  display: flex;
  justify-content: center;
  background: white;
  padding: 10px;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.search-box {
  width: 600px;
  position: relative;
}
.player-info {
  flex: 0 0 auto;
  margin-top: 10px;
  padding: 10px;
  background: #f8f9fa;
  border-radius: 4px;
  font-size: 14px;
  text-align: center;
}
.player-header {
  display: flex;
  align-items: center;
  padding: 10px;
}
.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 2px solid #ccc;
  margin-right: 16px;
}
.info-text {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.name-cn {
  font-size: 20px;
  font-weight: bold;
}
.name-en {
  font-size: 16px;
  color: #666;
}
.team {
  display: flex;
  align-items: center;
  margin-top: 5px;
}
.team-logo {
  width: 24px;
  height: 24px;
  margin-right: 6px;
}
.chart-container {
  width: 100%;
  height: 750px;
}
.player-list-grid {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  max-height: 220px;
  overflow-y: auto;
  z-index: 101;
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 10px;
  padding: 10px;
}
.player-item-grid {
  cursor: pointer;
  text-align: center;
}
.player-item-grid:hover {
  background-color: #f5f5f5;
  border-radius: 4px;
}
.player-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-bottom: 6px;
}
.player-name {
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
