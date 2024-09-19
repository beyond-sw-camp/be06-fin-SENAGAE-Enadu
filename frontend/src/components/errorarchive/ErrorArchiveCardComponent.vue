<template>
  <div class="PostCard_block__FTMsy">
    <div class="header">
      <div class="user-info">
        <a class="PostCard_userInfo__Cu1X5" :href="'/@' + errorarchiveCard.nickname + '/posts'">
          <img
            loading="lazy"
            width="40"
            height="40"
            decoding="async"
            :src="errorarchiveCard.profileImg"
            alt="Profile Image"
            class="PostCard_avatar"
          />
        </a>
        <div class="details">
          <a class="nickname">{{ errorarchiveCard.nickname }}</a>
          <div class="PostCard_subInfo__KqVkC">
            <span>{{ formatDateTime(errorarchiveCard.createAt) }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="PostCard_content__W3lPm">
      <div class="PostCard_title">{{ errorarchiveCard.title }}</div>
    </div>

    <div class="PostCard_categories">
      <span>{{ errorarchiveCard.superCategory }}</span>
      <span v-if="errorarchiveCard.subCategory">/ {{ errorarchiveCard.subCategory }}</span>
    </div>

    <div class="PostCard_footer__9J5Wd">
      <div class="PostCard_likes___lp_I">
        <svg viewBox="0 0 24 24">
          <path fill="currentColor" d="m18 1-6 4-6-4-6 5v7l12 10 12-10V6z"></path>
        </svg>
        <span>{{ errorarchiveCard.likeCnt }}</span>
      </div>
      <div class="PostCard_author__nickname">
        <span>{{ errorarchiveCard.nickname }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { formatDateTime } from '@/utils/FormatDate';
export default {
  name: "ErrorArchiveCardComponent",
  data() {
    return {};
  },
  props: ["errorarchiveCard"],
  methods: {
    formatDateTime
    },
    components: {},
    mounted() { 
    console.log(this.errorarchiveCard);
  },
};
</script>

<style scoped>
/* 기존 스타일 유지 */
.PostCard_block__FTMsy {
  /* border-left: 1.5px solid #8d8d8d; */
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  width: 100%;
  background: #fff;
  box-shadow: 5px 5px 7px 0 rgba(0, 0, 0, 0.08);
  transition: box-shadow .25s ease-in, transform .25s ease-in;
}

.PostCard_block__FTMsy:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 20px 0 rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.user-info {
  display: flex;
  align-items: center;
}

.PostCard_userInfo__Cu1X5 {
  margin-right: 0.5rem;
  display: flex;
  align-items: center;
}

.PostCard_avatar {
  object-fit: cover;
  border-radius: 50%;
  width: 40px;
  height: 40px;
}

.details {
  display: flex;
  flex-direction: column;
}

.nickname {
  font-weight: bold;
  color: #212529;
}

.PostCard_subInfo__KqVkC {
  font-size: .75rem;
  color: #868e96;
}

.PostCard_content__W3lPm {
  box-sizing: inherit;
  margin: 0;
  padding: 1rem;
  display: flex;
  flex-direction: column;
}

.PostCard_title {
  font-size: 1rem;
  margin: 0 0 .25rem;
  line-height: 1.5;
  word-break: break-word;
  color: #212529;
}

.PostCard_footer__9J5Wd {
  border-radius: 0 0 15px 15px;
  background-color: #f8f9fa;
  padding: .625rem 1rem;
  border-top: 1px solid #f1f3f5;
  display: flex;
  justify-content: space-between;
  align-items: center; /* 수직 중앙 정렬 */
}

.PostCard_likes___lp_I {
  display: flex;
  align-items: center;
  color: #212529;
}

.PostCard_likes___lp_I > svg {
  width: .75rem;
  height: .75rem;
  margin-right: .5rem;
}
.PostCard_author__nickname {
  display: flex;
  align-items: center;
  font-size: 0.875rem;
  color: #212529;
}


</style>
