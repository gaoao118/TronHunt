import request from '@/utils/request'

// 查询目标账号列表
export function listGold(query) {
  return request({
    url: '/tron/gold/list',
    method: 'get',
    params: query
  })
}

// 查询目标账号详细
export function getGold(id) {
  return request({
    url: '/tron/gold/' + id,
    method: 'get'
  })
}

// 新增目标账号
export function addGold(data) {
  return request({
    url: '/tron/gold',
    method: 'post',
    data: data
  })
}

// 修改目标账号
export function updateGold(data) {
  return request({
    url: '/tron/gold',
    method: 'put',
    data: data
  })
}

// 删除目标账号
export function delGold(id) {
  return request({
    url: '/tron/gold/' + id,
    method: 'delete'
  })
}
