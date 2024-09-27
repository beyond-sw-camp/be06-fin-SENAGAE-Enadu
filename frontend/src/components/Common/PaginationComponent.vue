<template>
  <div v-show="isLoading" class="ui pagination menu">
    <a class="item" @click="setPrevPage">&lt;&lt;</a>
    <a
        v-for="number in pageNumbers"
        :key="number"
        :class="{'active item': currentPage === number, 'item': currentPage !== number}"
        @click="setActivePage(number)"
    >
      {{ number }}
    </a>
    <a class="item" @click="setNextPage">&gt;&gt;</a>
  </div>
</template>

<script>
export default {
  name: "PaginationComponent",
    props: {
        totalPage: {
            type: Number,
            required: true
        },
        nowPage: {
            type: Number,
            default: 1
        },
    },
  data() {
    return {
      currentPage: 1,
      pageNumbers: [],
      isLoading: false,
    };
  },
  methods: {
    setActivePage(number) {
      this.currentPage = number;
      this.$emit('updatePage', this.currentPage);
    },
    setPrevPage() {
      if (this.currentPage === 1) {
        return;
      }
      this.currentPage--;
      if (this.currentPage % 5 === 0) {
        this.pageNumbers = [];
        for (let page = this.currentPage - 4; page < this.currentPage + 1; page++) {
          this.pageNumbers.push(page);
        }
      }
      this.$emit('updatePage', this.currentPage);
    },
    setNextPage() {
      if (this.currentPage === this.totalPage) {
        return;
      }
      this.currentPage++;
      if (this.currentPage % 5 === 1) {
        this.pageNumbers = [];
        for (let page = this.currentPage; page < this.currentPage + 5 ; page++) {
          if (page > this.totalPage) {
            break;
          }
          this.pageNumbers.push(page);
        }
      }
      this.$emit('updatePage', this.currentPage);
    },
  },
  created() {
    for (let page = 1; page < this.totalPage + 1; page++) {
      if (page > 5){
        break;
      }
      this.pageNumbers.push(page);
    }
    this.currentPage = this.nowPage;
    this.isLoading = true;
  }
};
</script>

<style>
.ui.menu {
    display: flex;
    margin: 1rem 0;
    background: #fff;
    border: 1px solid rgba(34, 36, 38, 0.15);
    border-radius: 0.28571429rem;
    padding: 0;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    font-weight: 500;
    min-height: 0;
}
.item {
    padding: 0.5em 1em;
    cursor: pointer;
    color: rgba(0, 0, 0, 0.8);
    transition: background-color 0.3s, color 0.3s;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1rem;
    border: 0.2px solid rgba(34, 36, 38, 0.1);
}

.item:first-child {
    border-left: none;
}

.item:hover {
    background-color: rgba(34, 36, 38, 0.1);
}

.ui.pagination.menu .active.item {
    border-top: none;
    border-top-width: initial;
    border-top-color: initial;
    background-color: #e1e8e8;
    color: rgba(0, 0, 0, .95);
    -webkit-box-shadow: none;
    box-shadow: none;
}
</style>