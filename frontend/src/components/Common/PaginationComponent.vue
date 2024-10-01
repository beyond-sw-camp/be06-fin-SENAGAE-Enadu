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
        for (let page = this.currentPage; page < this.currentPage + 5; page++) {
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
    console.log(this.totalPage);
    for (let page = 1; page < this.totalPage + 1; page++) {
      if (page > 5) {
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
.item {
  padding: 8px 12px; /* 패딩 조정 */
  margin: 0; /* 마진 조정 */
  border: 1px solid #dee2e6; /* 각 li의 테두리 색상 */
  color: #007bff; /* 기본 텍스트 색상 */
  text-decoration: none; /* 밑줄 제거 */
  font-weight: 400; /* 글씨 굵기 조정 */
  transition: background-color 0.3s, color 0.3s; /* 부드러운 전환 효과 */
  min-width: 30px; /* 최소 너비 설정 */
  text-align: center; /* 텍스트 중앙 정렬 */
}

.item:hover {
  background-color: #e9ecef; /* 호버 시 배경색 */
  color: #0056b3; /* 호버 시 텍스트 색상 */
}

.active.item {
  background-color: #007bff; /* 현재 선택한 페이지 배경색 */
  color: white; /* 현재 선택한 페이지 텍스트 색상 */
  border: 1px solid #007bff; /* 현재 선택한 페이지 테두리 색상 */
}

.item:disabled {
  color: #6c757d; /* 비활성화된 텍스트 색상 */
  cursor: not-allowed; /* 비활성화된 커서 */

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
}
</style>
