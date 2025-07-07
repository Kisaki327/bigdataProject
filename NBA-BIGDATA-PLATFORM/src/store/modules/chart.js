const state = {
  charts: {},
  currentChartId: null
}

const mutations = {
  SET_CHART(state, { id, option, type }) {
    state.charts[id] = { option, type }
    state.currentChartId = id
  },
  SET_CURRENT_CHART_ID(state, id) {
    if (state.charts[id]) state.currentChartId = id
  },
  CLEAR_CHARTS(state) {
    state.charts = {}
    state.currentChartId = null
  }
}

const actions = {
  setChart({ commit }, payload) {
    commit('SET_CHART', payload)
  },
  setCurrentChartId({ commit }, id) {
    commit('SET_CURRENT_CHART_ID', id)
  },
  clearCharts({ commit }) {
    console.log('clearCharts')
    commit('CLEAR_CHARTS')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
}
