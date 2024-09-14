<template>
  <div class="answer-write-component">
    <div class="ripple-component">
      <div class="mantine-17p85na">
        <div class="re-comment-arrow mantine-155ln8z">
          <svg
              aria-hidden="true"
              focusable="false"
              data-prefix="fas"
              data-icon="arrow-turn-down-right"
              class="svg-inline--fa fa-arrow-turn-down-right"
              role="img"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 512 512"
              style="font-size: 16px; color: #dee2e6"
          >
            <path
                fill="currentColor"
                d="M64 64c0-17.7-14.3-32-32-32S0 46.3 0 64V224c0 53 43 96 96 96H402.7l-73.4 73.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0l128-128c12.5-12.5 12.5-32.8 0-45.3l-128-128c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L402.7 256H96c-17.7 0-32-14.3-32-32V64z"
            ></path>
          </svg>
        </div>
        <div class="re-comment mantine-5n4x4z">
          <div class="mantine-5n4x4z">
            <div class="mantine-Stack-root mantine-1ttseea">
              <div class="mantine-2j9uwr">
                <div class="mantine-1uguyhf">
                  <a
                      class="mantine-Avatar-root mantine-18l6s08"
                  ><img
                      class="mantine-9rx0rd mantine-Avatar-image"
                      src='{{ qnaComment.profileImage }}'
                  /></a>
                  <div class="mantine-Stack-root mantine-1l47z8p">
                    <div class="mantine-824czz">
                      <a
                          class="mantine-Text-root mantine-3qdwx9"
                      >{{ qnaComment.nickname }}</a
                      >
                      <div class="mantine-Badge-root mantine-11jjpd0">
                              <span class="mantine-1jlwn9k mantine-Badge-inner"
                              >{{ qnaComment.grade }}</span
                              >
                      </div>
                    </div>
                    <p class="mantine-Text-root mantine-1q4x896">
                      {{ formatDateTime(qnaComment.createdAt) }}
                    </p>
                  </div>
                </div>
              </div>
              <div class="mantine-6va1do">
                <div class="markdown-body mantine-0">
                  <p>{{ qnaComment.answerComment }}</p>
                  <p>
                    저 Child()라는 것이 자식클래스에 생성자를 넣고 싶으신 것 같은데,
                    내부 코드까지 다 작성해주셔야 알 수 있습니다. 번거롭더라도 전체
                    코드예시를 주시면 좋겠습니다.
                  </p>
                  <p>&nbsp;</p>
                  <p>
                    Child로 생성한 객체의 인스턴스 변수 x가 10으로 세팅되려면,
                    말씀하신 대로 생성자를 통해서 하면 정상적으로 동작하긴 합니다.
                  </p>
                  <p>
                    예를 들어 자식클래스(Child) 안 쪽에 아래와 같이 추가하면 Child의
                    x값도 10이 됩니다.
                  </p>
                  <p>&nbsp;</p>
                </div>
              </div>
              <div class="mantine-12kvhdu">
                <div class="mantine-Group-root mantine-1qj7haw">
                  <button
                      class="mantine-UnstyledButton-root mantine-Button-root mantine-k7c4r8"
                      type="button"
                      data-button="true"
                      aria-label="답글 작성하기"
                  >
                    <div class="mantine-1yjkc96 mantine-Button-inner">
                            <span
                                class="mantine-Button-icon mantine-Button-leftIcon mantine-19ok7fu"
                            ><svg
                                aria-hidden="true"
                                focusable="false"
                                data-prefix="far"
                                data-icon="comment"
                                class="svg-inline--fa fa-comment"
                                role="img"
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 512 512"
                                style="font-size: 14px; color: inherit"
                            >
                                <path
                                    fill="currentColor"
                                    d="M123.6 391.3c12.9-9.4 29.6-11.8 44.6-6.4c26.5 9.6 56.2 15.1 87.8 15.1c124.7 0 208-80.5 208-160s-83.3-160-208-160S48 160.5 48 240c0 32 12.4 62.8 35.7 89.2c8.6 9.7 12.8 22.5 11.8 35.5c-1.4 18.1-5.7 34.7-11.3 49.4c17-7.9 31.1-16.7 39.4-22.7zM21.2 431.9c1.8-2.7 3.5-5.4 5.1-8.1c10-16.6 19.5-38.4 21.4-62.9C17.7 326.8 0 285.1 0 240C0 125.1 114.6 32 256 32s256 93.1 256 208s-114.6 208-256 208c-37.1 0-72.3-6.4-104.1-17.9c-11.9 8.7-31.3 20.6-54.3 30.6c-15.1 6.6-32.3 12.6-50.1 16.1c-.8 .2-1.6 .3-2.4 .5c-4.4 .8-8.7 1.5-13.2 1.9c-.2 0-.5 .1-.7 .1c-5.1 .5-10.2 .8-15.3 .8c-6.5 0-12.3-3.9-14.8-9.9c-2.5-6-1.1-12.8 3.4-17.4c4.1-4.2 7.8-8.7 11.3-13.5c1.7-2.3 3.3-4.6 4.8-6.9c.1-.2 .2-.3 .3-.5z"
                                ></path></svg></span
                            ><span class="mantine-1ryt1ht mantine-Button-label">답글</span>
                    </div>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {formatDateTime} from "@/utils/FormatDate";

export default {
  name: "QnaCommentDetailComponent",
  data() {
    return {};
  },
  props: ["qnaComment"],
  methods: {
    formatDateTime
  },
  mounted() {
  },
  components: {},
}
</script>

<style>
.answer-write-component {
  margin-bottom: 40px;
}

</style>