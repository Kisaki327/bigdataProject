import request from '@/utils/request'

// 查询NBA球员基本信息列表
export function listPlayers(query) {
  return request({
    url: '/system/players/list',
    method: 'get',
    params: query
  })
}

// 查询NBA球员基本信息详细
export function getPlayers(playerId) {
  return request({
    url: '/system/players/' + playerId,
    method: 'get'
  })
}

// 新增NBA球员基本信息
export function addPlayers(data) {
  return request({
    url: '/system/players',
    method: 'post',
    data: data
  })
}

// 修改NBA球员基本信息
export function updatePlayers(data) {
  return request({
    url: '/system/players',
    method: 'put',
    data: data
  })
}

// 删除NBA球员基本信息
export function delPlayers(playerId) {
  return request({
    url: '/system/players/' + playerId,
    method: 'delete'
  })
}

//按球队所在地获取球员数量
export function playerNumByTeamCity(){
  return request({
    url: '/system/players/groupByCity',
    method: 'get'
  })
}

export function playerInfo(season, playerId) {
  return request({
    url: '/system/players/getPlayers',
    method: 'get',
    params: {
      season,
      ...(playerId ? { playerId } : {})
    }
  })
}

export function top10Players(season,orderBy) {
  return request({
    url: '/system/players/getTop10',
    method: 'get',
    params: {
      season,
      sort:orderBy || null
    }
  })
}

export function PlayerDist(season){
  return request({
    url: '/system/players/getPlayerDist',
    method: 'get',
    params: {
      season
    }
  })
}

export function playerPos(season,pos){
  return request({
    url: '/system/players/getPlayersPos',
    method: 'get',
    params: {
      season,
      pos
    }

  })
}

export function PlayerGames(){
  return request({
    url: '/system/players/getPlayerGames',
    method: 'get',
  })
}
