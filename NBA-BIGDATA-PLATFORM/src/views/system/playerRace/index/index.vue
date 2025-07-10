<template>
  <div class="app-container">
    <div class="toolbar">
      <el-select v-model="seasonYear" placeholder="选择赛季" @change="loadData" style="width: 150px;">
        <el-option v-for="year in yearOptions" :key="year" :label="year" :value="year" />
      </el-select>

      <el-checkbox-group v-model="selectedDimensions" @change="updateCharts">
        <el-checkbox v-for="dim in allDimensions" :label="dim.key" :key="dim.key">{{ dim.label }}</el-checkbox>
      </el-checkbox-group>

      <el-switch v-model="isDark" active-text="暗色" inactive-text="亮色" @change="updateCharts" />
    </div>

    <el-row :gutter="20">
      <el-col v-for="dim in selectedDimensions" :key="dim" :span="24">
          <div :id="'barChart-' + dim" class="chart-box"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { playerInfo } from '@/api/system/players'

export default {
  data() {
    return {
      players: [],
      seasonYear: '2024',
      yearOptions: [...Array(22).keys()].map(i => String(2003 + i)),
      allDimensions: [
        { key: 'pointspg', label: '场均得分' },
        { key: 'rebspg', label: '场均篮板' },
        { key: 'assistspg', label: '场均助攻' },
        { key: 'stealspg', label: '场均抢断' },
        { key: 'blockspg', label: '场均盖帽' },
        { key: 'per', label: 'PER' }
      ],
      selectedDimensions: ['pointspg', 'rebspg'],
      isDark: false,
      chartMap: {}
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    async loadData() {
      const res = await playerInfo(this.seasonYear)
      this.players = res.data || []
      this.updateCharts()
    },
    updateCharts() {
      this.selectedDimensions.forEach(dim => this.renderBarChart(dim))
    },
    getChartTitle(dimKey) {
      const dim = this.allDimensions.find(d => d.key === dimKey)
      return `${this.seasonYear} ${dim.label} 排名前10`
    },
    renderBarChart(dimKey) {
      const dimInfo = this.allDimensions.find(d => d.key === dimKey)
      const sorted = [...this.players]
        .filter(p => p[dimKey] !== undefined && p[dimKey] !== null)
        .sort((a, b) => b[dimKey] - a[dimKey])
        .slice(0, 10)

      const yAxisData = sorted.map(p => p.name)
      const rich = {}
      sorted.forEach((p, i) => {
        rich[`img${i}`] = {
          height: 15,
          width: 15,
          align: 'center',
          backgroundColor: { image: p.avatar }
        }
      })

      const chartDom = document.getElementById('barChart-' + dimKey)
      if (!chartDom) return
      if (this.chartMap[dimKey]) this.chartMap[dimKey].dispose()
      const chart = echarts.init(chartDom, this.isDark ? 'dark' : 'light')
      this.chartMap[dimKey] = chart

      chart.setOption({
        title: { show: true },
        toolbox: {
          show: true,
          orient: 'horizontal',
          left: 'right',
          top: 'top',
          itemSize: 20,
          iconStyle: {
            borderColor: '#333'
          },
          feature: {
            saveAsImage: {
              show: true,
              title: '保存图片'
            },
            dataView: {
              show: true,
              title: '查看数据',
              readOnly: true
            },
            restore: {
              show: true,
              title: '还原'
            },
            dataZoom: {
              show: true
            }
          }
        },
        grid: { left: 100, right: 20, top: 10, bottom: 30 },
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'shadow' },
          formatter: params => {
            const data = params[0].data
            return `${data.name}<br/>${dimInfo.label}: ${data.value}`
          }
        },
        xAxis: {
          type: 'value',
          axisLabel: { color: '#ffffff' }
        },
        yAxis: {
          type: 'category',
          data: yAxisData,
          axisLabel: {
            formatter: (value, idx) => `{img${idx}|} ${value}`,
            rich,
            color: '#ffffff'
          },
          axisTick: { show: false }
        },
        series: [{
          name: dimInfo.label,
          type: 'bar',
          data: sorted.map(p => ({ name: p.name, value: p[dimKey] })),
          itemStyle: { color: '#3BA272' },
          label: {
            show: true,
            position: 'right',
            color: this.isDark ? '#eee' : '#333'
          }
        }]
      })

      window.addEventListener('resize', () => chart.resize())

      this.$store.dispatch('chart/setChart', {
        id: chart.getOption().title[0]?.text || dimInfo.label,
        option: chart.getOption(),
        type: 'bar'
      })
    }
  },
  beforeDestroy() {
    this.$store.dispatch('chart/clearCharts')
  }
}
</script>

<style lang="scss" scoped>
  .app-container {
  min-height: 100vh;
  background-color: rgba(0, 10, 30, 0.85);
  background-image: linear-gradient(45deg, rgba(255,255,255,0.05) 25%, transparent 25%),
                    linear-gradient(-45deg, rgba(255,255,255,0.05) 25%, transparent 25%),
                    linear-gradient(45deg, transparent 75%, rgba(255,255,255,0.05) 75%),
                    linear-gradient(-45deg, transparent 75%, rgba(255,255,255,0.05) 75%);
  background-size: 40px 40px;
  background-position: 0 0, 0 20px, 20px -20px, -20px 0px;
  background-attachment: fixed;
  padding: 20px;
  .chart-card {
    background-color: #ffffff;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    padding: 20px;
    margin-bottom: 24px;
    margin-bottom: 20px;
    text-align: center;
    font-weight: bolder;
    transition: box-shadow 0.3s ease;

    &:hover {
      box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
    }
  }
}
.toolbar {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 20px;
  flex-wrap: wrap;
}
.chart-box {
  flex: 1 1 calc(50% - 20px);
  height: 45vh;
  min-width: 400px;
  background-color: rgba(10, 10, 30, 0.85);
  border-radius: 12px;
  padding: 10px;
  box-shadow: 0 0 12px rgba(0, 255, 255, 0.12);
  transition: transform 0.3s ease;
  position: relative;
}

.chart-box:hover {
  transform: scale(1.02);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.3);
}
</style>
