<template>
  <div class="app-container">
    <div style="margin-bottom: 10px; display: flex; align-items: center; gap: 20px;">
      <el-select v-model="seasonYear" placeholder="选择赛季" @change="loadData" style="width: 150px;">
        <el-option v-for="year in yearOptions" :key="year" :label="year" :value="year" />
      </el-select>

      <el-checkbox-group v-model="selectedDimensions" @change="updateCharts">
        <el-checkbox v-for="dim in allDimensions" :label="dim.key" :key="dim.key">{{ dim.label }}</el-checkbox>
      </el-checkbox-group>

      <el-switch v-model="isDark" active-text="暗色" inactive-text="亮色" @change="updateCharts" />
    </div>

    <el-row :gutter="20">
      <el-col v-for="dim in selectedDimensions" :key="dim" :span="12">
        <div class="chart-container">
          <div :id="'barChart-' + dim" style="width: 100%; height: 500px;"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { playerInfo } from '@/api/system/players';

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
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      const res = await playerInfo(this.seasonYear);
      this.players = res.data || [];
      this.updateCharts();
    },

    updateCharts() {
      this.selectedDimensions.forEach(dim => this.renderBarChart(dim));
    },

    renderBarChart(dimKey) {
      const dimInfo = this.allDimensions.find(d => d.key === dimKey);
      const sorted = [...this.players]
        .filter(p => p[dimKey] !== undefined && p[dimKey] !== null)
        .sort((a, b) => b[dimKey] - a[dimKey])
        .slice(0, 10);

      const yAxisData = sorted.map(p => p.name);
      const rich = {};

      sorted.forEach((p, i) => {
        rich[`img${i}`] = {
          height: 15,
          width: 15,
          align: 'center',
          backgroundColor: {
            image: p.avatar
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
          text: `${this.seasonYear} ${dimInfo.label} 排名前20`,
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
          data: sorted.map(p => ({ name: p.name, value: p[dimKey] })),
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
      window.addEventListener('resize', () => {
        chart.resize();
      });
      this.$store.dispatch('chart/setChart', {
        id:chart.getOption().title[0].text,
        option: chart.getOption(),
        type:chart.getOption().series[0].type
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
  padding: 20px;
}
.chart-container {
  margin-bottom: 30px;
}
</style>
