import request from '@/utils/request'
import {extractChartSummary} from '@/utils/chartParse'

export function explain(content) {
  return request({
    url: '/system/ai/explain',
    method: 'post',
     timeout: 60000,
    data: {
      content: content
    }
  })
}

export function wxyyExplain(content,chartId) {
  content = extractChartSummary(content)
  return request({
    url: '/system/ai/wxyyExplain',
    method: 'post',
     timeout: 60000,
    data: {
      content: '请帮我解读这个echarts图表,图表的option如下：' + JSON.stringify(content),
      chartId: chartId
    }
  })
}
