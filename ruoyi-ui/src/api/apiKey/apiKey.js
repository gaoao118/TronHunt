import request from '@/utils/request'

// 查询请求key列表
export function listApiKey(query) {
  return request({
    url: '/apiKey/apiKey/list',
    method: 'get',
    params: query
  })
}

// 查询请求key详细
export function getApiKey(id) {
  return request({
    url: '/apiKey/apiKey/' + id,
    method: 'get'
  })
}

// 新增请求key
export function addApiKey(data) {
  return request({
    url: '/apiKey/apiKey',
    method: 'post',
    data: data
  })
}

// 修改请求key
export function updateApiKey(data) {
  return request({
    url: '/apiKey/apiKey',
    method: 'put',
    data: data
  })
}

// 删除请求key
export function delApiKey(id) {
  return request({
    url: '/apiKey/apiKey/' + id,
    method: 'delete'
  })
}
