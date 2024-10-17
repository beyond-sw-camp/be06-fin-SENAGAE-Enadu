<template>
  <router-link :to="{ path: '/errorarchive/detail', query: { id: errorarchiveCard.id }}">
    <div class="PostCard_block__FTMsy">
      <div class="header">
        <div class="user-info">
          <a class="PostCard_userInfo__Cu1X5" :href="'/@' + errorarchiveCard.nickname + '/posts'">
            <img
                loading="lazy"
                decoding="async"
                :src="errorarchiveCard.profileImg"
                alt="Profile Image"
                class="PostCard_avatar"
            />
          </a>
          <div class="details">
            <a class="nickname">{{ errorarchiveCard.nickname }}</a>
            <div class="PostCard_subInfo__KqVkC">
              <span>{{ formatDateTime(errorarchiveCard.createdAt) }}</span>
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
  </router-link>
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
  },
};
</script>

<style scoped>
.PostCard_block__FTMsy {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  width: 100%;
  max-height: 200px;
  overflow: hidden;
  background: #fff;
  box-shadow: 2px 2px 3px rgba(0, 0, 0, 0.15);
  transition: box-shadow .25s ease-in, transform .25s ease-in;
  border-radius: 15px;
}

.PostCard_block__FTMsy:hover {
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.15);
}

.header {
  display: flex;
  align-items: center;
  padding: 1rem 0.8rem;
}

.user-info {
  display: flex;
  align-items: center;
}

.PostCard_userInfo__Cu1X5 {
  margin-right: 0.5rem;
  display: flex;
  align-items: center;
  width: 35px;
}

.PostCard_avatar {
  object-fit: cover;
  border-radius: 50%;
  width: 35px;
  height: 35px;
}

.details {
  display: flex;
  flex-direction: column;
  max-width: 130px;
}

.nickname {
  font-weight: bold;
  color: #212529;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.PostCard_subInfo__KqVkC {
  font-size: .75rem;
  color: #868e96;
}

.PostCard_content__W3lPm {
  box-sizing: inherit;
  margin: 0;
  padding: 0.5rem 1rem;
  display: flex;
  flex-direction: column;
}

.PostCard_title {
  font-size: 1rem;
  font-weight: 600;
  line-height: 1.5;
  word-break: break-word;
  color: #212529;
  overflow: hidden; /* 내용이 넘치면 숨김 */
  text-overflow: ellipsis; /* 생략 부호 추가 */
  white-space: nowrap; /* 한 줄로 표시 */
}

.PostCard_categories {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  color: var(--main-color);
  font-size: 0.8em;
  font-weight: 500;
  text-align: right;
  margin-left: auto;
  white-space: nowrap;
  max-width: 150px;
  padding: 8px;
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

.PostCard_author__nickname > span {
  max-width: 130px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.PostCard_categories > span {
  margin-right: 5px;
}

</style>
