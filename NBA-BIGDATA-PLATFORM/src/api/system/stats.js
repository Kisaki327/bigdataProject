import request from '@/utils/request'

// 查询球队统计信息列表
export function listStats(query) {
  return request({
    url: '/system/stats/list',
    method: 'get',
    params: query
  })
}

// 查询球队统计信息详细
export function getStats(id) {
  return request({
    url: '/system/stats/' + id,
    method: 'get'
  })
}

// 新增球队统计信息
export function addStats(data) {
  return request({
    url: '/system/stats',
    method: 'post',
    data: data
  })
}

// 修改球队统计信息
export function updateStats(data) {
  return request({
    url: '/system/stats',
    method: 'put',
    data: data
  })
}

// 删除球队统计信息
export function delStats(id) {
  return request({
    url: '/system/stats/' + id,
    method: 'delete'
  })
}

export function teams(season){
  return request({
    url: '/system/stats/getTeams',
    method: 'get',
    params: {
      season
    }
  })
}
