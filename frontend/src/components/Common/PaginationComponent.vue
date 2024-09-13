<template>
  <div class="ui pagination menu">
    <a
        v-for="number in pageNumbers"
        :key="number"
        :class="{'active item': currentPage === number, 'item': currentPage !== number}"
        @click="setActivePage(number)"
    >
      {{ number }}
    </a>
  </div>
</template>

<script>
export default {
  name: "PaginationComponent",
  data() {
    return {
      currentPage: 1,
      pageNumbers: [1, 2, 3, 4, 5],
    };
  },
  methods: {
    setActivePage(number) {
      this.currentPage = number
      this.$emit('updatePage',this.currentPage);
    },
  },
};
</script>

<style>
.ui.pagination.menu .active.item {
  border-top: none;
  border-top-width: initial;
  border-top-style: none;
  border-top-color: initial;
  padding-top: .92857143em;
  background-color: #e1e8e8;
  color: rgba(0, 0, 0, .95);
  -webkit-box-shadow: none;
  box-shadow: none;
}
.ui.menu {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  margin: 1rem 0;
  font-family: Lato, 'Helvetica Neue', Arial, Helvetica, sans-serif;
  background: #fff;
  font-weight: 900;
  border: 1px solid rgba(34, 36, 38, .15);
  -webkit-box-shadow: 0 1px 2px 0 rgba(34, 36, 38, .15);
  box-shadow: 0 1px 2px 0 rgba(34, 36, 38, .15);
  border-radius: .28571429rem;
  min-height: 0px;
}
</style>