import request from '@/utils/request'

// 查询波场靓号列表
export function listGoodAddress(query) {
  return request({
    url: '/tron/goodAddress/list',
    method: 'get',
    params: query
  })
}

// 查询波场靓号详细
export function getGoodAddress(id) {
  return request({
    url: '/tron/goodAddress/' + id,
    method: 'get'
  })
}

// 新增波场靓号
export function addGoodAddress(data) {
  return request({
    url: '/tron/goodAddress',
    method: 'post',
    data: data
  })
}

// 修改波场靓号
export function updateGoodAddress(data) {
  return request({
    url: '/tron/goodAddress',
    method: 'put',
    data: data
  })
}

// 删除波场靓号
export function delGoodAddress(id) {
  return request({
    url: '/tron/goodAddress/' + id,
    method: 'delete'
  })
}
