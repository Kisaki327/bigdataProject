<template>
  <div class="app-container">
    <div style="margin-bottom: 10px; display: flex; align-items: center; gap: 20px;">
      <el-select v-model="seasonYear" placeholder="选择赛季" @change="loadData" style="width: 150px;">
        <el-option v-for="year in yearOptions" :key="year" :label="year" :value="year" />
      </el-select>

      <el-select v-model="raceNum" placeholder="排名数量" @change="updateCharts" style="width: 100px;">
        <el-option v-for = "race in raceNumOptions" :key="race" :label="race" :value="race"/>
      </el-select>

      <el-checkbox-group v-model="selectedDimensions" @change="updateCharts">
        <el-checkbox v-for="dim in allDimensions" :label="dim.key" :key="dim.key">{{ dim.label }}</el-checkbox>
      </el-checkbox-group>

      <el-switch v-model="isDark" active-text="暗色" inactive-text="亮色" @change="updateCharts" />
    </div>

        <div class="chart-container" >
          <div v-for="dim in selectedDimensions" :key="dim" :id="'barChart-' + dim" class="chart-box"></div>
        </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { teams } from '@/api/system/stats';

export default {
  data() {
    return {
      data: [],
      seasonYear: '2024',
      yearOptions: [...Array(22).keys()].map(i => String(2003 + i)),
      allDimensions: [
        { key: 'pointspg', label: '场均得分' },
        { key: 'rebspg', label: '场均篮板' },
        { key: 'assistspg', label: '场均助攻' },
        { key: 'stealspg', label: '场均抢断' },
        { key: 'blockspg', label: '场均盖帽' },
        { key: 'defrebs', label:'防守篮板数'},
        { key: 'turnovers', label:'失误次数'},
        { key: 'fouls', label:'犯规次数'}

      ],
      selectedDimensions: ['pointspg', 'rebspg', 'assistspg','stealspg'],
      isDark: false,
      chartMap: {},
      raceNum:10,
      raceNumOptions:[5,10,15,20,25,30]
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      const res = await teams(this.seasonYear);
      this.data = res.data || [];
      this.updateCharts();
    },

    updateCharts() {
      this.selectedDimensions.forEach(dim => this.renderBarChart(dim));
    },

    renderBarChart(dimKey) {
      const dimInfo = this.allDimensions.find(d => d.key === dimKey);
      const sorted = [...this.data]
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

      const chartDom = document.getElementById('barChart-' + dimKey);
      if (!chartDom) return;

      if (this.chartMap[dimKey]) this.chartMap[dimKey].dispose();
      const chart = echarts.init(chartDom, this.isDark ? 'dark' : 'light');
      this.chartMap[dimKey] = chart;

      chart.setOption({
        title: {
          text: `${this.seasonYear} ${dimInfo.label} 排名前${this.raceNum}`,
          left: 'center',
          textStyle: {
            color:  '#ffffff'
          }
        },
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
            color: '#ffffff'
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
            color: '#ffffff'
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
}
  },
  beforeDestroy(){
    this.$store.dispatch('chart/clearCharts')
  }
};
</script>

<style scoped>
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
.chart-container {
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
</style>
