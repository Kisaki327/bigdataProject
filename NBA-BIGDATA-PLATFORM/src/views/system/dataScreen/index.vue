<template>
  <div class="app-container">
    <el-container style="height: 100vh;">
      <el-aside style="width: 300px; ">
          <el-select v-model="selectYear" placeholder="请选择赛季" style="width: 150px;" @change="updateCharts">
            <el-option
              v-for="item in yearOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
          <el-switch v-model="isFivePlyaers" active-text="显示前五名" inactive-text="不显示前五名"/>
          <el-row v-for="items in playersPosData" :key="items.pos">
             <div class="info-container" v-for="(item,index) in items.data" :key="index" >
              <!-- 左侧文字 -->
              <div class="text-info" v-if="index === 0 || index > 0 && isFivePlyaers">
                <p>最强{{ items.pos }} #{{ index + 1 }}</p>
                <p>{{ item.name }}</p>
                <p>{{ item.firstNameEn }}-{{ item.enName }}</p>
                <p>{{ item.teamname }} #{{ item.num }}</p>
                <p>分数：{{ item.points }}</p>
              </div>

              <!-- 右侧图像 -->
              <div class="image-info" v-if="index === 0 || index > 0 && isFivePlyaers">
                <el-image :src="item.logo" style="width: 50px; height: 50px; margin-bottom: 10px;" />
                <el-image :src="item.avatar" style="width: 100px; height: 100px;" />
              </div>
            </div>
            <el-divider/>
          </el-row>
      </el-aside>

      <el-container>
        <el-header style="height: 10vh;">
          <div class="header-content">
            NBA球赛大数据分析平台数据大屏
          </div>
        </el-header>

        <el-main>
          <div class="charts-grid">
            <div id="map-container" class="chart-box full-width"></div>
            <div id="top10-container" class="chart-box"></div>
            <div id="ScatterChart" class="chart-box"></div>
            <div id="FunnelChart" class="chart-box"></div>
            <div id="TeamsChart" class="chart-box"></div>
          </div>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import { playerPos,top10Players,playerNumByTeamCity,PlayerDist } from '@/api/system/players'
import { teams } from '@/api/system/stats';
import * as echarts from 'echarts'
import axios from 'axios'
export default {
  data() {
    return {
      playersPosData: [],
      top10PlayersData: [],
      playerMapChartData:[],
      posInfo: ['前锋','中锋','后卫'],
      selectYear: '2024',
      yearOptions: [...Array(22).keys()].map(i => String(2003 + i)),
      top10Chart:null,
      playerMapChart:null,
      PlayerDistData:[],
      scatterChart:null,
      scatterOption: {},
      funnelOption: {},
      funnelChart:null,
      teamsData:[],
      isFivePlyaers: false,
      allDimensions: [
        { key: 'pointspg', label: '场均得分' },
      ],
      selectedDimensions: ['pointspg'],
      isDark: false,
      chartMap: {},
      raceNum:10,
      raceNumOptions:[5,10,15,20,25,30]
    }
  },
 mounted() {
    this.updateCharts()
  },
  beforeDestroy() {
    this.top10Chart.dispose();
    this.playerMapChart.dispose();
    this.$store.dispatch('chart/clearCharts')
  },
  methods: {
    async updateCharts(){
      this.initCharts();
      await this.loadData();
      this.drawPlayerMapChart(this.playerMapChartData)
      this.drawTop10Chart(this.top10PlayersData)
      this.setOptions();
      this.drawScatterAndFunnelChart()
      this.selectedDimensions.forEach(dim => this.drawTeamsChart(dim));
    },
    initCharts() {
      this.top10Chart = echarts.init(document.getElementById('top10-container'))
      this.playerMapChart = echarts.init(document.getElementById('map-container'))
      this.scatterChart = echarts.init(document.getElementById("ScatterChart"));
      this.funnelChart = echarts.init(document.getElementById("FunnelChart"));
    },
    async loadData() {
      this.playersPosData = [];
      for (const pos of this.posInfo) {
        const res = await playerPos(this.selectYear, pos);
        this.playersPosData.push({
          pos,
          data: res.data
        });
      }
      const res = await top10Players(this.selectYear);
      this.top10PlayersData = res.data;

      const res2 = await playerNumByTeamCity();
      this.playerMapChartData = res2.data;

      const res3 = await PlayerDist(this.selectYear);
      this.PlayerDistData = res3.data;

      const res4 = await teams(this.selectYear);
      this.teamsData = res4.data;
},
      drawTop10Chart(data) {
      const rich = {};
      const yAxisData = data.map((p, i) => {
        const key = `img${i}`;
        rich[key] = {
          height: 30,
          align: 'center',
          backgroundColor: { image: p.avatar }
        };
        return `{${key}|} {name|${p.name}}`;
      });

      const scores = data.map(p => Number(p.points));

      const option = {
        title: { text: `${this.selectYear} 赛季得分前10`, left: 'center' },
        tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
        grid: { left: '5%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'value', name: '得分' },
        yAxis: {
          type: 'category',
          inverse: true,
          data: yAxisData,
          axisLabel: {
            formatter: val => val,
            rich: {
              ...rich,
              name: { fontSize: 14, padding: [0, 0, 0, 6] }
            }
          }
        },
        series: [{
          name: '得分',
          type: 'bar',
          data: scores,
          label: { show: true, position: 'right' },
          itemStyle: { color: '#409EFF' }
        }]
      };

      this.top10Chart.setOption(option, true);
      window.addEventListener('resize', () => {
        this.top10Chart.resize();
      });
      this.$store.dispatch('chart/setChart', {
        id:option.title.text,
        option: option,
        type:option.series[0].type
      });
    },
    async drawPlayerMapChart(data){
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
      data.forEach(({ teamCity, count }) => {
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
      this.playerMapChart.setOption({
        title: { text: 'NBA球员数量分州统计', left: 'center' },
        tooltip: { trigger: 'item', formatter: '{b}: {c} 人' },
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
      this.$store.dispatch('chart/setChart', {
        id: this.playerMapChart.getOption().title[0].text,
        option: this.playerMapChart.getOption(),
        type: 'map'
      })
    },

     processFunnelData() {
      const bins = [
        { name: '0-5', min: 0, max: 5, count: 0 },
        { name: '5-10', min: 5, max: 10, count: 0 },
        { name: '10-15', min: 10, max: 15, count: 0 },
        { name: '15-20', min: 15, max: 20, count: 0 },
        { name: '20-25', min: 20, max: 25, count: 0 },
        { name: '25-30', min: 25, max: 30, count: 0 },
        { name: '30+', min: 30, max: Infinity, count: 0 },
      ];

      this.PlayerDistData.forEach(player => {
        const ppg = player.pointSpg;
        for (let bin of bins) {
          if (ppg >= bin.min && ppg < bin.max) {
            bin.count++;
            break;
          }
        }
      });

      const total = this.PlayerDistData.length;
      return bins.map(bin => ({
        name: `${bin.name} (${((bin.count / total) * 100).toFixed(1)}%)`,
        value: bin.count
      }));
    },

    setOptions() {
      // 散点图
      const scatterData = this.PlayerDistData.map(p => [p.per, p.pointSpg]);

      this.scatterOption = {
        title: {
          text: `${this.selectYear} PER-场均得分关系图`,
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: (params) => `PER: ${params.value[0]}<br>pointSpg: ${params.value[1]}`
        },
        xAxis: {
          name: 'PER',
          type: 'value'
        },
        yAxis: {
          name: '场均得分',
          type: 'value'
        },
        series: [
          {
            name: '球员',
            type: 'scatter',
            symbolSize: 10,
            data: scatterData,
            itemStyle: {
              color: '#3BA272'
            }
          }
        ]
      };

      // 漏斗图
      const funnelData = this.processFunnelData();

      this.funnelOption = {
        title: {
          text: `${this.selectYear} 球员场均得分区间分布`,
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>人数: {c}'
        },
        series: [
          {
            name: 'PER 区间',
            type: 'funnel',
            left: '10%',
            top: 60,
            bottom: 20,
            width: '80%',
            sort: 'descending',
            gap: 2,
            label: {
              show: true,
              position: 'inside'
            },
            data: funnelData
          }
        ]
      };
      this.$store.dispatch('chart/setChart',{
        id: this.scatterOption.title.text,
        option: this.scatterOption,
        type: 'scatter'
      })
      this.$store.dispatch('chart/setChart',{
        id: this.funnelOption.title.text,
        option: this.funnelOption,
        type: 'funnel'
      })
    },

    drawScatterAndFunnelChart() {
      this.scatterChart.setOption(this.scatterOption);
      this.funnelChart.setOption(this.funnelOption);
      window.addEventListener('resize', () => {
        this.scatterChart.resize();
        this.funnelChart.resize();
      });
    },

    drawTeamsChart(dimKey) {
      const dimInfo = this.allDimensions.find(d => d.key === dimKey);
      const sorted = [...this.teamsData]
        .filter(p => p[dimKey] !== undefined && p[dimKey] !== null)
        .sort((a, b) => b[dimKey] - a[dimKey])
        .slice(0, this.raceNum);

      const yAxisData = sorted.map(p => p.teamname);
      const rich = {};

      sorted.forEach((p, i) => {
        rich[`img${i}`] = {
          height: 15,
          width: 15,
          align: 'center',
          backgroundColor: {
            image: p.logoDark
          }
        };
      });

      const chartDom = document.getElementById('TeamsChart');
      if (!chartDom) return;

      if (this.chartMap[dimKey]) this.chartMap[dimKey].dispose();
      const chart = echarts.init(chartDom, this.isDark ? 'dark' : 'light');
      this.chartMap[dimKey] = chart;

      chart.setOption({
        title: {
          text: `${this.selectYear} ${dimInfo.label} 排名前${this.raceNum}`,
          left: 'center',
          textStyle: {
            color: this.isDark ? '#eee' : '#333'
          }
        },
        grid: {
          left: 120,
          right: 30,
          top: 50,
          bottom: 50
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter: params => {
            const data = params[0].data;
            return `${data.name}<br/>${dimInfo.label}: ${data.value}`;
          }
        },
        xAxis: {
          type: 'value',
          axisLabel: {
            color: this.isDark ? '#ccc' : '#333'
          }
        },
        yAxis: {
          type: 'category',
          data: yAxisData,
          axisLabel: {
            formatter: (value, idx) => `{img${idx}|} ${value}`,
            rich,
            color: this.isDark ? '#ccc' : '#333'
          },
          axisTick: { show: false }
        },
        series: [{
          name: dimInfo.label,
          type: 'bar',
          data: sorted.map(p => ({ name: p.teamname, value: p[dimKey] })),
          itemStyle: {
            color: '#5470c6'
          },
          label: {
            show: true,
            position: 'right',
            color: this.isDark ? '#eee' : '#333'
          }
        }]
      });
      this.$store.dispatch('chart/setChart', {
        id:chart.getOption().title[0].text,
        option: chart.getOption(),
        type:chart.getOption().series[0].type
      });

      window.addEventListener('resize', () => {
        chart.resize();
      });
    },
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
}


  .sidebar-container {
  height: 100%;
  overflow-y: auto;
  padding: 10px;
  background-color: #0a0a0ab7;
}
.info-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
}

.text-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  flex: 1;
}

.image-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 25px;
}

.header-content {
  border: 1px solid #000000;
  text-align: center;
  font-size: 32px;
  font-weight: bold;
  color: #ffffff;
  background: linear-gradient(90deg, #007cf0, #00dfd8);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;

  padding: 20px 0;
  margin-bottom: 20px;

  text-shadow: 0 0 10px rgba(0, 140, 255, 0.5),
               0 0 20px rgba(0, 255, 255, 0.3);

  font-family: 'Microsoft YaHei', 'Segoe UI', 'Arial', sans-serif;

  animation: fadeInDown 1s ease-out both;
}

@keyframes fadeInDown {
  0% {
    opacity: 0;
    transform: translateY(-30px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

.charts-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
  padding: 20px;
  height: calc(90vh - 40px);
  overflow-y: auto;
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

.full-width {
  flex: 1 1 100%;
  height: 50vh;
}

</style>
