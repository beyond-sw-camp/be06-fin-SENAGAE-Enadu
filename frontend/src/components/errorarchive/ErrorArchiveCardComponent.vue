<template>
    <div class="container">
      <div class="PostCard_block__FTMsy">
        <div class="PostCard_content__W3lPm">
          <div class="PostCard_title">{{ errorarchiveCard.title }}</div>
          <!-- <div class="PostCard_descriptionWrapper__UQSKA">
              <p class="PostCard_title">그것이야말로 「행복」에 다다르는 길!</p>
            </div> -->
          <div class="PostCard_subInfo__KqVkC">
            <span>{{ errorarchiveCard.createAt }}</span>
          </div>
        </div>
        <div class="PostCard_footer__9J5Wd">
          <a class="PostCard_userInfo__Cu1X5" href="/@juunini/posts">
            <img 
              loading="lazy" 
              width="24" 
              height="24" 
              decoding="async" 
              :src="errorarchiveCard.profileImage" 
              alt="Profile Image"
              style="color: transparent;"
            />
            <span>by <b>{{ errorarchiveCard.nickname }}</b></span> 
          </a>
          <div class="PostCard_likes___lp_I">
            <svg viewBox="0 0 24 24">
              <path fill="currentColor" d="m18 1-6 4-6-4-6 5v7l12 10 12-10V6z"></path>
            </svg>
            <span>{{ errorarchiveCard.likeCnt }}</span> <!-- 바인딩 수정 -->
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "ErrorArchiveCardComponent",
    props: {
      errorarchiveCard: {
        type: Object,
        required: true,
      }
    },
  };
  </script>
  
  <style scoped>
  /* 전역 스타일은 제거했습니다. 여기에서의 스타일은 각 컴포넌트에만 적용됩니다. */
  
  .PostCard_block__FTMsy {
    border-left: 1.5px solid #8d8d8d;
    box-sizing: border-box;
    display: flex;
    flex-flow: column;
    width: 100%;
    background: #fff;
    box-shadow: 5px 5px 7px 0 rgba(0, 0, 0, 0.08);
    transition: box-shadow .25s ease-in, transform .25s ease-in;
  }
  
  .PostCard_block__FTMsy:hover {
    transform: translateY(-5px);
    box-shadow: 0 12px 20px 0 rgba(0, 0, 0, 0.1);
  }
  
  .PostCard_content__W3lPm {
    box-sizing: inherit;
    margin: 0;
    padding: 1rem;
    display: flex;
    flex: 1 1;
    flex-direction: column;
  }
  
  .PostCard_title {
    font-size: 1rem;
    margin: 0 0 .25rem;
    line-height: 1.5;
    word-break: break-word;
    color: #212529;
  }
  
  .PostCard_subInfo__KqVkC {
    margin-top: auto;
    font-size: .75rem;
    line-height: 1.5;
    color: #868e96;
  }
  
  .PostCard_footer__9J5Wd {
    border-radius: 0 0 15px 15px;
    background-color: #f8f9fa;
    padding: .625rem 1rem;
    border-top: 1px solid #f1f3f5;
    display: flex;
    font-size: .75rem;
    line-height: 1.5;
    justify-content: space-between;
  }
  
  .PostCard_userInfo__Cu1X5 {
    text-decoration: none;
    color: inherit;
    display: flex;
    align-items: center;
  }
  
  .PostCard_userInfo__Cu1X5 > img {
    object-fit: cover;
    border-radius: 50%;
    width: 1.5rem;
    height: 1.5rem;
    display: block;
    margin-right: .5rem;
  }
  
  .PostCard_userInfo__Cu1X5 span {
    color: #868e96;
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
  </style>
  