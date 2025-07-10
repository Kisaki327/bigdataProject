<template>
  <div class="app-container">
    <!-- 控制区 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-switch
          v-model="isDark"
          active-text="暗色模式"
          inactive-text="明亮模式"
          @change="onThemeChange"
        />
      </el-col>
      <el-col :span="6">
        <el-switch
          v-model="autoPlay"
          active-text="自动轮播"
          inactive-text="手动切换"
          @change="onAutoPlayChange"
        />
      </el-col>
      <el-col :span="6">
        <el-select v-model="selectedYear" placeholder="选择年份" @change="updateBarChart">
          <el-option
            v-for="year in yearOptions"
            :key="year"
            :label="year"
            :value="year"
          />
        </el-select>
      </el-col>
      <el-col :span="6">
        <el-select v-model="selectedSeasonYear" placeholder="选择赛季年份" @change="updateSeasonChart">
          <el-option
            v-for="year in yearOptions"
            :key="year + '-season'"
            :label="year"
            :value="year"
          />
        </el-select>
      </el-col>
    </el-row>
    <el-divider/>
    <!-- 图表区 -->
    <el-row :gutter="20">
      <el-col :span="24">
        <div ref="dynamicBarChart"  class="chart-box" ></div>
      </el-col>
      <el-col :span="24">
        <div ref="seasonBarChart"  class="chart-box" ></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { top10Players } from '@/api/system/players';

export default {
  data() {
    return {
      // 通用
      yearOptions: [
        '2003', '2004', '2005', '2006', '2007', '2008', '2009', '2010',
        '2011', '2012', '2013', '2014', '2015', '2016', '2017', '2018',
        '2019', '2020', '2021', '2022', '2023', '2024'
      ],
      isDark: false,
      autoPlay: false,
      timer: null,
      animationDuration: 2000,

      // 动态图
      selectedYear: '2003',
      players: [],
      allPlayersByYear: {},
      barChart: null,

      // 静态图
      selectedSeasonYear: '2024',
      allSeasonDataByYear: {},
      seasonChart: null
    };
  },

  async mounted() {
    this.initCharts();
    await Promise.all([
      this.loadYearData(this.selectedYear),
      this.loadSeasonYearData(this.selectedSeasonYear)
    ]);
    this.updateBarChart();
    this.updateSeasonChart();
    this.preloadOtherYears();
    if (this.autoPlay) this.startAutoPlay();
  },

  methods: {
    initCharts() {
      if (this.barChart) echarts.dispose(this.barChart);
      if (this.seasonChart) echarts.dispose(this.seasonChart);
      this.barChart = echarts.init(this.$refs.dynamicBarChart, this.isDark ? 'dark' : null);
      this.seasonChart = echarts.init(this.$refs.seasonBarChart, this.isDark ? 'dark' : null);
    },

    async loadYearData(year) {
      if (!this.allPlayersByYear[year]) {
        try {
          const res = await top10Players(year, '66'); // 生涯累计得分
          this.allPlayersByYear[year] = res.data || [];
        } catch (e) {
          this.allPlayersByYear[year] = [];
          console.error(`加载 ${year} 生涯数据失败`, e);
        }
      }
      return this.allPlayersByYear[year];
    },

    async loadSeasonYearData(year) {
      if (!this.allSeasonDataByYear[year]) {
        try {
          const res = await top10Players(year); // 单赛季得分
          this.allSeasonDataByYear[year] = res.data || [];
        } catch (e) {
          this.allSeasonDataByYear[year] = [];
          console.error(`加载 ${year} 赛季数据失败`, e);
        }
      }
      return this.allSeasonDataByYear[year];
    },

    async preloadOtherYears() {
      const others = this.yearOptions.filter(y => y !== this.selectedYear && y !== this.selectedSeasonYear);
      const concurrency = 3;
      for (let i = 0; i < others.length; i += concurrency) {
        const batch = others.slice(i, i + concurrency);
        await Promise.all(batch.map(y => Promise.all([
          this.loadYearData(y),
          this.loadSeasonYearData(y)
        ])));
        await new Promise(res => setTimeout(res, 200));
      }
    },

    async updateBarChart() {
      await this.loadYearData(this.selectedYear);
      this.players = this.allPlayersByYear[this.selectedYear];
      this.drawBarChart();
    },

    drawBarChart() {
      const rich = {};
      const yAxisData = this.players.map((p, i) => {
        const key = `img${i}`;
        rich[key] = {
          height: 30,
          align: 'center',
          backgroundColor: { image: p.avatar }
        };
        return `{${key}|} {name|${p.name}}`;
      });

      const scores = this.players.map(p => Number(p.careerPoints));

      const option = {
        title: { text: `${this.selectedYear} 生涯得分前10`, left: 'center' },
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
        tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
        grid: { left: '5%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'value', name: '得分', max: 'dataMax' },
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
          realtimesort: true,
          label: { show: true, position: 'right', valueAnimation: true },
          itemStyle: { color: '#67C23A' },
          animationDurationUpdate: this.animationDuration
        }],
        animationDuration: this.animationDuration,
        animationEasing: 'linear',
        animationEasingUpdate: 'linear'
      };

      this.barChart.setOption(option, false);
      window.addEventListener('resize', () => {
        this.barChart.resize();
      });
      this.$store.dispatch('chart/setChart', {
        id:option.title.text,
        chart: this.seasonChart,
        type:option.series[0].type
      });
    },

    async updateSeasonChart() {
      await this.loadSeasonYearData(this.selectedSeasonYear);
      const data = this.allSeasonDataByYear[this.selectedSeasonYear];
      this.drawSeasonChart(data);
    },

    drawSeasonChart(data) {
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
        title: { text: `${this.selectedSeasonYear} 赛季得分前10`, left: 'center' },
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

      this.seasonChart.setOption(option, true);
      window.addEventListener('resize', () => {
        this.seasonChart.resize();
      });
      this.$store.dispatch('chart/setChart', {
        id:option.title.text,
        option: option,
        type:option.series[0].type
      });
    },

    startAutoPlay() {
      clearInterval(this.timer);
      this.timer = setInterval(() => {
        this.selectedYear = this.getNextYear(this.selectedYear);
        this.updateBarChart();
      }, this.animationDuration+100);
    },

    getNextYear(current) {
      const idx = this.yearOptions.indexOf(current);
      return this.yearOptions[(idx + 1) % this.yearOptions.length];
    },

    onThemeChange() {
      this.initCharts();
      this.updateBarChart();
      this.updateSeasonChart();
    },

    onAutoPlayChange() {
      if (this.autoPlay) {
        this.startAutoPlay();
      } else {
        clearInterval(this.timer);
      }
    }
  },

  beforeDestroy() {
    clearInterval(this.timer);
    this.$store.dispatch('chart/clearCharts')
  }
};
</script>

<style scoped lang="scss">
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
