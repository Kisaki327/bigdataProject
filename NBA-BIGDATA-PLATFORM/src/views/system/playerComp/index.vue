<template>
  <div class="player-compare-header">
    <div class="header-title">球员对比</div>
    <el-row class="compare-body">
      <!-- 左侧球员 -->
      <el-col :span="10" class="player-card">
        <el-row :gutter="0" style="height: 100%;">
          <el-col :span="4">
            <img class="team-logo" :src="left.logo" />
          </el-col>
          <el-col :span="10">
            <div style="height: 100%; width: 100%; position: relative;">
              <img class="player-img" :src="left.avatar" />
              <el-button class="switch-btn" size="small" type="primary" @click="switchPlayer('left')">切换⇌</el-button>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="player-info">
              <div>{{ left.teamname }} | #{{ left.num }} | {{left.playerAddr}}</div>
              <div class="player-name-cn">{{ left.name }}</div>
              <div class="player-name-en">{{ left.firstNameEn + left.enName }}</div>
            </div>
          </el-col>
        </el-row>
      </el-col>

      <!-- 中间 VS -->
      <el-col :span="4" class="vs-box">
        <div class="vs-text">球员对比<br />VS</div>
      </el-col>

      <!-- 右侧球员 -->
      <el-col :span="10" class="player-card">
        <el-row :gutter="0" style="height: 100%;">
          <el-col :span="4">
            <img class="team-logo" :src="right.logo" />
          </el-col>
          <el-col :span="10">
            <div style="height: 100%; width: 100%; position: relative;">
              <img class="player-img" :src="right.avatar" />
              <el-button class="switch-btn" size="small" type="primary" @click="switchPlayer('right')">切换⇌</el-button>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="player-info">
              <div>{{ right.teamname }} | #{{ right.num }} | {{ right.playerAddr }}</div>
              <div class="player-name-cn">{{ right.name }}</div>
              <div class="player-name-en">{{ right.firstNameEn + right.enName }}</div>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

    <!-- 对比图与选项 -->
    <el-container style="margin-top: 100px; background: white; min-height: 300px;">
      <!-- 左侧选项栏 -->
      <el-aside width="220px" style="padding: 20px; background: #f6f9fc;">
        <el-form label-position="top">
          <el-form-item label="赛季年份">
            <el-select v-model="seasonYear" placeholder="选择年份">
              <el-option v-for="year in yearOptions" :key="year" :label="year + ' 赛季'" :value="year" />
            </el-select>
          </el-form-item>

          <el-form-item label="图表类型">
            <el-radio-group v-model="chartType">
              <el-radio-button label="radar">雷达图</el-radio-button>
              <el-radio-button label="bar">柱状图</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="主题">
            <el-switch
              v-model="isDark"
              active-value="dark"
              inactive-value="light"
              @change="toggleDarkMode"
            />
            <span style="margin-left: 10px; color: #409EFF;">深色模式</span>
          </el-form-item>
        </el-form>
      </el-aside>
      <!-- 右侧图表 -->
      <el-main style="padding: 0;">
        <div id="chart" style="width: 100%; height: 360px;"></div>
      </el-main>
    </el-container>
    <!-- 搜索弹窗 -->
    <el-dialog :visible.sync="searchDialogVisible"  width="500px" :before-close="handleClose" custom-class="custom-dialog-bg">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入球员姓名"
        clearable
        @input="handleSearch"
      >
      <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
    </el-input>
      <div class="search-results">
        <el-row :gutter="20">
          <el-col
            v-for="player in filteredPlayers"
            :key="player.playerid"
            :span="12"
            class="result-card"
            @click.native="selectPlayer(player.playerid)"
          >
            <div class="result-item">
              <img :src="player.avatar" class="search_player-avatar" />
              <img :src="player.logo" class="search_team-logo" />
              <div class="player-names">
                <div class="cn-name">{{ player.name }}</div>
                <div class="en-name">{{ player.enName }}</div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { playerInfo } from '@/api/system/players'
import tex from 'highlight.js/lib/languages/tex';
export default {
  data() {
    return {
      seasonYear: '2024',
      chartType: 'radar',
      isDark: 'light',
      yearOptions: [
        '2024', '2023', '2022', '2021', '2020', '2019', '2018', '2017', '2016', '2015',
        '2014', '2013', '2012', '2011', '2010', '2009', '2008', '2007', '2006', '2005',
        '2004', '2003'
      ],
      myChart : null,
      searchDialogVisible: false,
      searchKeyword: '',
      filteredPlayers: [],
      allPlayers: [],// 后端获取的球员数组
      left:{},
      right:{},
      choisePlayer: '',
      compData:[]
    }
  },
  watch: {
    chartType() {
    this.drawChart();
  },
  left: {
    deep: true,
    handler() {
      this.drawChart();
    }
  },
  right: {
    deep: true,
    handler() {
      this.drawChart();
    },
  },
  seasonYear:{
    deep: true,
    handler() {
      this.getData().then(() => {
        this.drawChart();
      });
    },
  },
  },
  mounted() {
   this.getData().then(() => {
    this.drawChart();
  });
  },
  methods: {

    toggleDarkMode(value){
      this.isDark =  value
      this.drawChart();
    },
    getRadarStats(player) {
    const toNum = v => isNaN(Number(v)) ? 0 : Number(v)
    return [
      toNum(player.games),
      parseFloat(player.pointspg),
      parseFloat(player.rebspg),
      parseFloat(player.assistspg),
      parseFloat(player.stealspg),
      parseFloat(player.blockspg),
      parseFloat(player.fgpctStr)/100,
      parseFloat(player.tppctStr)/100,
      parseFloat(player.ftpctStr)/100
    ]
  },
    async getData(){
      const res = await playerInfo(this.seasonYear);
      this.allPlayers = res.data
      this.left = this.allPlayers[0]
      this.right = this.allPlayers[1]
    },
    switchPlayer(player) {
      this.searchDialogVisible = true
      this.choisePlayer = player
    },
    handleSearch() {
    this.filteredPlayers = this.allPlayers.filter(p =>
      p.name.includes(this.searchKeyword) || p.enName.toLowerCase().includes(this.searchKeyword.toLowerCase())
    );
    },
    selectPlayer(id) {
      this.searchDialogVisible = false;
      if(this.choisePlayer === 'left'){
        this.left = this.allPlayers.find(p => p.playerid === id);
      }else if(this.choisePlayer === 'right'){
        this.right = this.allPlayers.find(p => p.playerid === id);
      }else{
        console.log('choisePlayer is null');
      }
    },
    handleClose() {
      this.searchKeyword = '';
      this.filteredPlayers = [];
      this.searchDialogVisible = false;
      this.$nextTick(() => {
      this.drawChart();
    });
    },
    drawChart() {
      const chartDom = document.getElementById('chart')
      if(this.myChart != null)  this.myChart.dispose()

      this.myChart = echarts.init(chartDom,this.isDark)
      let option = {}

      if (this.chartType === 'radar') {
        option = {
          title:{
            text: `${this.seasonYear} 赛季 ${this.left.name} VS ${this.right.name}`,
            left: 'center'
          },
          tooltip: {},
          toolbox: {
          show: true,
          left: 'right',
          top: 'top',
          feature: {
            dataView: { readOnly: false },
            restore: {},
            saveAsImage: {}
              }
           },
          radar: {
            indicator: [
              { name: '总场数', max: 80 },
              { name: '场均得分', max: 30 },
              { name: '场均篮板', max: 10 },
              { name: '场均助攻', max: 12 },
              { name: '场均抢断', max: 3 },
              { name: '场均盖帽', max: 2 },
              { name: '投篮命中率', max: 1 },
              { name: '三分命中率', max: 1 },
              { name: '罚球命中率', max: 1 }

            ]
          },
          series: [{
            type: 'radar',
            data: [
              {
                value: this.getRadarStats(this.left),
                name: this.left.name,
                areaStyle: {
                  color: 'rgba(0, 255, 255, 0.5)'
                }
              },
              {
                value: this.getRadarStats(this.right),
                name: this.right.name,
                areaStyle: {
                  color: 'rgba(255, 0, 255, 0.5)'
                }
              }
            ]
          }],
          alignTicks: false,
        }
      } else {
        option = {
           title:{
            text: `${this.seasonYear} 赛季 ${this.left.name} VS ${this.right.name}`,
            left: 'center'
          },
          toolbox: {
          show: true,
          left: 'right',
          top: 'top',
          feature: {
            dataView: { readOnly: false },
            restore: {},
            saveAsImage: {}
              }
           },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: [this.left.name, this.right.name]
          },
          xAxis: {
            type: 'category',
            data: ['总场数','场均得分', '场均篮板', '场均助攻', '场均抢断', '场均盖帽', '三分命中率','投篮命中率','罚球命中率']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: this.left.name,
              type: 'bar',
              data: this.getRadarStats(this.left),
            },
            {
              name: this.right.name,
              type: 'bar',
              data: this.getRadarStats(this.right),
            }
          ]
        }
      }
      this.$store.dispatch('chart/setChart',{
        id: option.title.text,
        option: option,
        type: this.chartType
      })
      this.myChart.setOption(option)

      window.addEventListener('resize', () => {
        this.myChart.resize()
      })
    }
  },
  beforeDestroy(){
    this.$store.dispatch('chart/clearCharts')
  }
}
</script>

<style scoped>
.player-compare-header {
  border-radius: 8px;
  overflow: hidden;
  background: #f0f2f5;
  box-shadow: #1e4ea9;
}

/* 顶部标题条 */
.header-title {
  background-color: #132b56;
  color: white;
  font-size: 20px;
  font-weight: bold;
  padding: 12px 20px;
}

/* 主体区域 */
.compare-body {
  background-color: rgba(36, 152, 198, 0.5);
  min-height: 200px;
  display: flex;
}

/* 球员卡片通用样式 */
.player-card {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
}

/* 球队 Logo */
.team-logo {
  width: 60px;
  margin-top: 20px;
  height: 60px;
}

/* 球员头像 */
.player-img {
  width: 200px;
  margin-top: 20px;
  height: 180px;
}

/* 切换按钮 */
.switch-btn {
  position: absolute;
  bottom: 20px;
  right: 20px;
}

/* 球员信息块 */
.player-info {
  margin-top: 10px;
  font-size: 14px;
}

.player-name-cn {
  font-size: 18px;
  font-weight: bold;
}

.player-name-en {
  font-size: 16px;
  color: #555;
}

.player-college {
  font-size: 14px;
  color: #777;
}

/* VS 分割区 */
.vs-box {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #1e4ea9;
  color: white;
  font-size: 30px;
  font-weight: bold;
  line-height: 1.5;
  height: 200px;
  border-radius: 1%;
}
.search-results {
  margin-top: 20px;
  max-height: 300px;
  overflow-y: auto;
}

.result-card {
  cursor: pointer;
  transition: all 0.2s ease;
}

.result-card:hover {
  background-color: #f5f7fa;
  border-radius: 8px;
}

.result-item {
  display: flex;
  align-items: center;
  padding: 2px;
}

.search_team-logo {
  width: 50px;
  height: 50px;
  margin-right: 5px;
}

.search_player-avatar {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  margin-right: 10px;
}

.player-names {
  display: flex;
  flex-direction: column;
}

.cn-name {
  font-weight: bold;
  font-size: 16px;
}

.en-name {
  font-size: 14px;
  color: #666;
}
/deep/.el-dialog {
  background-color: #f6f6f6 !important;
}

</style>
