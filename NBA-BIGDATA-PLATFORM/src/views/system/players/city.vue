<template>
  <div id="app-container" class="app-container">
    <el-container>
      <el-header>
        <el-select clearable v-model="value" placeholder="请选择" @change="onChartTypeChange">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-header>
      <el-main>
        <div ref="chartContainer" style="width:100%;height:600px;"></div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { playerNumByTeamCity } from '@/api/system/players'
import axios from 'axios'

export default {
  name: 'CityToStateMap',
  data() {
    return {
      options: [
        { value: '地图', label: '地图' },
        { value: '饼图', label: '饼图' }
      ],
      value: '地图',
      cityData: [],
      chart: null
    }
  },
  async mounted() {
    // 预先加载城市级数据
    try {
      const res = await playerNumByTeamCity()
      this.cityData = res.data   // [{ teamCity, count }, ...]
    } catch (err) {
      console.error('获取城市数据失败', err)
    }
    this.onChartTypeChange(this.value)
    window.addEventListener('resize', this.resizeChart)
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.resizeChart)
    if (this.chart) this.chart.dispose()
  },
  methods: {
    resizeChart() {
      if (this.chart) this.chart.resize()
    },
    async drawMap() {
      // 初始化或重建实例
      if (this.chart) this.chart.dispose()
      this.chart = echarts.init(this.$refs.chartContainer)
      this.chart.showLoading()

      // 1. 加载地图 geoJSON
      const geoRes = await axios.get('/sources/USA.json')
      echarts.registerMap('USA', geoRes.data, {
        Alaska: { left: -131, top: 25, width: 15 },
        Hawaii: { left: -110, top: 28, width: 5 },
        'Puerto Rico': { left: -76, top: 26, width: 2 }
      })

      // 2. 城市→州映射表
      const cityToState = {
        Memphis: 'Tennessee',        'San Antonio': 'Texas',
        Philadelphia: 'Pennsylvania','New York': 'New York',
        Phoenix: 'Arizona',          Minnesota: 'Minnesota',
        Houston: 'Texas',            Denver: 'Colorado',
        Miami: 'Florida',            Milwaukee: 'Wisconsin',
        LA: 'California',            'Los Angeles': 'California',
        Indiana: 'Indiana',          Charlotte: 'North Carolina',
        Cleveland: 'Ohio',           Chicago: 'Illinois',
        Detroit: 'Michigan',         Washington: 'District of Columbia',
        'New Orleans': 'Louisiana',  Utah: 'Utah',
        Portland: 'Oregon',          Atlanta: 'Georgia',
        Brooklyn: 'New York',        'Oklahoma City': 'Oklahoma',
        Boston: 'Massachusetts',     'Golden State': 'California',
        Sacramento: 'California',    Toronto: 'Ontario',
        Dallas: 'Texas',             Orlando: 'Florida'
      }

      // 3. 聚合到州
      const stateCount = {}
      this.cityData.forEach(({ teamCity, count }) => {
        const state = cityToState[teamCity] || `其他(${teamCity})`
        stateCount[state] = (stateCount[state] || 0) + count
      })

      // 4. 美国50州集合，用于标识“非美国”州
      const us50 = new Set([
        'Alabama','Alaska','Arizona','Arkansas','California','Colorado','Connecticut','Delaware',
        'Florida','Georgia','Hawaii','Idaho','Illinois','Indiana','Iowa','Kansas','Kentucky',
        'Louisiana','Maine','Maryland','Massachusetts','Michigan','Minnesota','Mississippi',
        'Missouri','Montana','Nebraska','Nevada','New Hampshire','New Jersey','New Mexico',
        'New York','North Carolina','North Dakota','Ohio','Oklahoma','Oregon','Pennsylvania',
        'Rhode Island','South Carolina','South Dakota','Tennessee','Texas','Utah','Vermont',
        'Virginia','Washington','West Virginia','Wisconsin','Wyoming'
      ])

      // 5. 构造 series.data，并标注非美国
      const seriesData = Object.entries(stateCount).map(([state, cnt]) => ({
        name: us50.has(state) ? state : `${state}（非美国）`,
        value: cnt
      }))

      // 6. 渲染地图
      this.chart.hideLoading()
      this.chart.setOption({
        title: { text: 'NBA球员数量分州统计', left: 'center' },
        tooltip: { trigger: 'item', formatter: '{b}: {c} 人' },
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
        visualMap: {
          min: 0,
          max: Math.max(...seriesData.map(d => d.value)),
          left: 'right', calculable: true,
          inRange: { color: ['#e0f3f8','#74add1','#313695'] }
        },
        toolbox: { show: true, feature: { restore: {}, saveAsImage: {} } },
        series: [{
          name: '球员数量',
          type: 'map',
          map: 'USA',
          roam: true,
          emphasis: { label: { show: true } },
          data: seriesData
        }]
      })
    },
    drawPie() {
      if (this.chart) this.chart.dispose()
      this.chart = echarts.init(this.$refs.chartContainer)

      // 构造饼图数据：直接用城市数据
      const pieData = this.cityData.map(({ teamCity, count }) => ({
        name: teamCity, value: count
      }))

      this.chart.setOption({
        title: { text: 'NBA球员数量城市分布', left: 'center' },
        tooltip: { trigger: 'item', formatter: '{b}: {c} 人 ({d}%)' },
        legend: { orient: 'vertical', left: 10, data: pieData.map(d => d.name) },
        toolbox: { show: true, feature: { restore: {}, saveAsImage: {} } },
        series: [{
          name: '球员数量',
          type: 'pie',
          radius: '60%',
          center: ['60%','50%'],
          data: pieData,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0,0,0,0.5)'
            }
          }
        }]
      })
    },
    onChartTypeChange(type) {
      if (type === '地图') {
        this.drawMap()
      } else {
        this.drawPie()
      }
    }
  }
}
</script>

<style scoped>
.app-container { min-height: 100vh;
  background-color: rgba(0, 10, 30, 0.85);
  background-image: linear-gradient(45deg, rgba(255,255,255,0.05) 25%, transparent 25%),
                    linear-gradient(-45deg, rgba(255,255,255,0.05) 25%, transparent 25%),
                    linear-gradient(45deg, transparent 75%, rgba(255,255,255,0.05) 75%),
                    linear-gradient(-45deg, transparent 75%, rgba(255,255,255,0.05) 75%);
  background-size: 40px 40px;
  background-position: 0 0, 0 20px, 20px -20px, -20px 0px;
  background-attachment: fixed;
  padding: 20px; }
</style>
