<template>
  <div class="content">
    <div class="question-component qna-background color">
      <div class="rounded-box p-5 mb-10 md:p-8 bento-card" style="background: #efefef; border: 1px solid #19192c">
        <div class="mb-10">
          <div class="flex justify-between">
            <div class="question-answer mb-5 build-section-card-title">
              Q.
              RestController 이렇게 쓰면 안되나요
            </div>
            <div
                class="w-16 h-8 transition"
            >
              <button class="red-button-size ui inverted red button">edit</button>
            </div>
          </div>
            <p class="text-sm">{{ hello }}</p>
        </div>
        <div class="" style="font-size: 13px">
          <div>
            <aside class="bg-black text-white p-6 rounded-lg w-full max-w-md font-mono">
              <div class="flex justify-between items-center">
                <div class="flex space-x-2 text-red-500">
                  <div class="w-3 h-3 rounded-full bg-red-500"></div>
                  <div class="w-3 h-3 rounded-full bg-yellow-500"></div>
                  <div class="w-3 h-3 rounded-full bg-green-500"></div>
                </div>
                <p class="text-sm">spring</p>
              </div>
              <div class="mt-4">
                <p class="text-green-400">@RestController</p>
                <p class="text-green-400">@RequestMapping("/test")</p>
                <p class="text-white">&nbsp;public class TestJenController {</p>
                <p class="text-green-400">&nbsp;&nbsp;@GetMapping()</p>
                <p class="text-white">&nbsp;&nbsp;public String saveTest() {</p>
                <p class="text-white">&nbsp;&nbsp;&nbsp;return "test";</p>
                <p class="text-white">&nbsp;&nbsp;}</p>
                <p class="text-white">}</p>
              </div>
            </aside>
          </div>
          <div class="mt-auto pt-10">
            <div class="flex flex-col md:flex-row gap-5 justify-start">
              <a
                  target="_blank"
                  rel="noopener noreferrer"
              ><span class="block px-4 py-2 cta-arrow-btn cta-arrow-verde"
              ><span class="flex items-center justify-center background-shadow"
              ><span class="relative inline-block">AI Answer</span
              ><span class="inline-block h-2 ml-3 button-icon"
              ><svg
                  style="
                            height: 100%;
                            width: auto;
                            max-height: 100%;
                            max-width: 100%;
                          "
                  viewBox="0 0 78 72"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
              >
                          <path
                              d="M41.7575 4.13232L73.625 35.9998L41.7575 67.8673M4.375 35.9998H72.7325"
                              stroke="currentColor"
                              stroke-width="12"
                              stroke-miterlimit="10"
                              stroke-linecap="round"
                              stroke-linejoin="round"
                          ></path></svg></span></span
              ><span style="display: block"
              ><span class="inline-block h-2 ml-3 button-icon"
              ></span></span></span></a
              ><a
                href="https://docs.bentoml.com/"
                target="_blank"
                rel="noopener noreferrer"
            ></a>
            </div>
          </div>
        </div>
      </div>
      <div class="answer-write-component"></div>
      <div class="answer-component qna-background color">
        <div class="rounded-box p-5 mb-10 md:p-8 bento-card" style="background: #efefef; border: 1px solid #19192c">
          <div class="mb-10">
            <div class="mantine-2j9uwr">
              <div class="mantine-1uguyhf">
                <a
                    class="mantine-Avatar-root mantine-18l6s09"
                    href="https://www.inflearn.com/users/14769/@weekendcode"
                ><img
                    class="mantine-9rx0rd mantine-Avatar-image"
                    src="https://cdn.inflearn.com/public/users/thumbnails/14769/aa751976-0548-441b-9084-d71ff6327348?w=76"
                    alt="주말코딩님의 프로필 이미지"
                /></a>
                <div class="mantine-Stack-root mantine-1l47z8p">
                  <div class="mantine-824czz">
                    <a
                        class="mantine-Text-root mantine-3qdwx9 answer-name-text"
                        href="https://www.inflearn.com/users/14769/@weekendcode"
                    >MR KIM</a
                    >
                    <div class="mantine-Badge-root mantine-11jjpd0">
                          <span class="mantine-1jlwn9k mantine-Badge-inner"
                          >답변자</span
                          >
                    </div>
                  </div>
                  <p class="mantine-Text-root mantine-1q4x896">
                    2024. 09. 04. 12:06
                  </p>
                </div>
              </div>
              <button class="red-button-size ui inverted red button">edit</button>
            </div>
            <div class="flex justify-between">
              <div id="answer-title-text" class="question-answer mb-5 build-section-card-title">
                Answer
              </div>
              <div
                  class="w-16 h-8 transition"
              >
              </div>
            </div>
            <p>
              BentoML’s code-first approach makes it highly flexible for developers to
              architect multi-model, multi-component distributed systems, offering the
              key building blocks for defining custom AI APIs, inference workers, async
              task queues, scaling behavior, batching optimization, and model
              composition.
            </p>
          </div>
          <div class="" style="font-size: 13px">
            <div>
              <aside class="bg-black text-white p-6 rounded-lg w-full max-w-md font-mono">
                <div class="flex justify-between items-center">
                  <div class="flex space-x-2 text-red-500">
                    <div class="w-3 h-3 rounded-full bg-red-500"></div>
                    <div class="w-3 h-3 rounded-full bg-yellow-500"></div>
                    <div class="w-3 h-3 rounded-full bg-green-500"></div>
                  </div>
                  <p class="text-sm">spring</p>
                </div>
                <div class="mt-4">
                  <p class="text-green-400">@RestController</p>
                  <p class="text-green-400">@RequestMapping("/test")</p>
                  <p class="text-white">&nbsp;public class TestJenController {</p>
                  <p class="text-green-400">&nbsp;&nbsp;@GetMapping()</p>
                  <p class="text-white">&nbsp;&nbsp;public String saveTest() {</p>
                  <p class="text-white">&nbsp;&nbsp;&nbsp;return "test";</p>
                  <p class="text-white">&nbsp;&nbsp;}</p>
                  <p class="text-white">}</p>
                </div>
              </aside>
            </div>
            <div class="mt-auto pt-10">
              <div class="flex flex-col md:flex-row gap-5 justify-start">
                <a
                    href="https://github.com/bentoml/rag-tutorials"
                    target="_blank"
                    rel="noopener noreferrer"
                ></a
                ><a
                  href="https://docs.bentoml.com/"
                  target="_blank"
                  rel="noopener noreferrer"
              ></a>
              </div>
            </div>
            <div class="like-dislike-container">
              <div class="icons-box">
                <div class="icons">
                  <label class="btn-label" for="like-checkbox">
                    <span class="like-text-content">123</span>
                    <input class="input-box" id="like-checkbox" type="checkbox">
                    <svg class="svgs" id="icon-like-solid" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                      <path d="M313.4 32.9c26 5.2 42.9 30.5 37.7 56.5l-2.3 11.4c-5.3 26.7-15.1 52.1-28.8 75.2H464c26.5 0 48 21.5 48 48c0 18.5-10.5 34.6-25.9 42.6C497 275.4 504 288.9 504 304c0 23.4-16.8 42.9-38.9 47.1c4.4 7.3 6.9 15.8 6.9 24.9c0 21.3-13.9 39.4-33.1 45.6c.7 3.3 1.1 6.8 1.1 10.4c0 26.5-21.5 48-48 48H294.5c-19 0-37.5-5.6-53.3-16.1l-38.5-25.7C176 420.4 160 390.4 160 358.3V320 272 247.1c0-29.2 13.3-56.7 36-75l7.4-5.9c26.5-21.2 44.6-51 51.2-84.2l2.3-11.4c5.2-26 30.5-42.9 56.5-37.7zM32 192H96c17.7 0 32 14.3 32 32V448c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32V224c0-17.7 14.3-32 32-32z"></path>
                    </svg>
                    <svg class="svgs" id="icon-like-regular" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                      <path d="M323.8 34.8c-38.2-10.9-78.1 11.2-89 49.4l-5.7 20c-3.7 13-10.4 25-19.5 35l-51.3 56.4c-8.9 9.8-8.2 25 1.6 33.9s25 8.2 33.9-1.6l51.3-56.4c14.1-15.5 24.4-34 30.1-54.1l5.7-20c3.6-12.7 16.9-20.1 29.7-16.5s20.1 16.9 16.5 29.7l-5.7 20c-5.7 19.9-14.7 38.7-26.6 55.5c-5.2 7.3-5.8 16.9-1.7 24.9s12.3 13 21.3 13L448 224c8.8 0 16 7.2 16 16c0 6.8-4.3 12.7-10.4 15c-7.4 2.8-13 9-14.9 16.7s.1 15.8 5.3 21.7c2.5 2.8 4 6.5 4 10.6c0 7.8-5.6 14.3-13 15.7c-8.2 1.6-15.1 7.3-18 15.1s-1.6 16.7 3.6 23.3c2.1 2.7 3.4 6.1 3.4 9.9c0 6.7-4.2 12.6-10.2 14.9c-11.5 4.5-17.7 16.9-14.4 28.8c.4 1.3 .6 2.8 .6 4.3c0 8.8-7.2 16-16 16H286.5c-12.6 0-25-3.7-35.5-10.7l-61.7-41.1c-11-7.4-25.9-4.4-33.3 6.7s-4.4 25.9 6.7 33.3l61.7 41.1c18.4 12.3 40 18.8 62.1 18.8H384c34.7 0 62.9-27.6 64-62c14.6-11.7 24-29.7 24-50c0-4.5-.5-8.8-1.3-13c15.4-11.7 25.3-30.2 25.3-51c0-6.5-1-12.8-2.8-18.7C504.8 273.7 512 257.7 512 240c0-35.3-28.6-64-64-64l-92.3 0c4.7-10.4 8.7-21.2 11.8-32.2l5.7-20c10.9-38.2-11.2-78.1-49.4-89zM32 192c-17.7 0-32 14.3-32 32V448c0 17.7 14.3 32 32 32H96c17.7 0 32-14.3 32-32V224c0-17.7-14.3-32-32-32H32z"></path>
                    </svg>
                    <div class="fireworks">
                      <div class="checked-like-fx"></div>
                    </div>
                  </label>
                </div>
                <div class="icons">
                  <label class="btn-label" for="dislike-checkbox">
                    <input class="input-box" id="dislike-checkbox" type="checkbox">
                    <div class="fireworks">
                      <div class="checked-dislike-fx"></div>
                    </div>
                    <svg class="svgs" id="icon-dislike-solid" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                      <path d="M313.4 32.9c26 5.2 42.9 30.5 37.7 56.5l-2.3 11.4c-5.3 26.7-15.1 52.1-28.8 75.2H464c26.5 0 48 21.5 48 48c0 18.5-10.5 34.6-25.9 42.6C497 275.4 504 288.9 504 304c0 23.4-16.8 42.9-38.9 47.1c4.4 7.3 6.9 15.8 6.9 24.9c0 21.3-13.9 39.4-33.1 45.6c.7 3.3 1.1 6.8 1.1 10.4c0 26.5-21.5 48-48 48H294.5c-19 0-37.5-5.6-53.3-16.1l-38.5-25.7C176 420.4 160 390.4 160 358.3V320 272 247.1c0-29.2 13.3-56.7 36-75l7.4-5.9c26.5-21.2 44.6-51 51.2-84.2l2.3-11.4c5.2-26 30.5-42.9 56.5-37.7zM32 192H96c17.7 0 32 14.3 32 32V448c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32V224c0-17.7 14.3-32 32-32z"></path>
                    </svg>
                    <svg class="svgs" id="icon-dislike-regular" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                      <path d="M323.8 34.8c-38.2-10.9-78.1 11.2-89 49.4l-5.7 20c-3.7 13-10.4 25-19.5 35l-51.3 56.4c-8.9 9.8-8.2 25 1.6 33.9s25 8.2 33.9-1.6l51.3-56.4c14.1-15.5 24.4-34 30.1-54.1l5.7-20c3.6-12.7 16.9-20.1 29.7-16.5s20.1 16.9 16.5 29.7l-5.7 20c-5.7 19.9-14.7 38.7-26.6 55.5c-5.2 7.3-5.8 16.9-1.7 24.9s12.3 13 21.3 13L448 224c8.8 0 16 7.2 16 16c0 6.8-4.3 12.7-10.4 15c-7.4 2.8-13 9-14.9 16.7s.1 15.8 5.3 21.7c2.5 2.8 4 6.5 4 10.6c0 7.8-5.6 14.3-13 15.7c-8.2 1.6-15.1 7.3-18 15.1s-1.6 16.7 3.6 23.3c2.1 2.7 3.4 6.1 3.4 9.9c0 6.7-4.2 12.6-10.2 14.9c-11.5 4.5-17.7 16.9-14.4 28.8c.4 1.3 .6 2.8 .6 4.3c0 8.8-7.2 16-16 16H286.5c-12.6 0-25-3.7-35.5-10.7l-61.7-41.1c-11-7.4-25.9-4.4-33.3 6.7s-4.4 25.9 6.7 33.3l61.7 41.1c18.4 12.3 40 18.8 62.1 18.8H384c34.7 0 62.9-27.6 64-62c14.6-11.7 24-29.7 24-50c0-4.5-.5-8.8-1.3-13c15.4-11.7 25.3-30.2 25.3-51c0-6.5-1-12.8-2.8-18.7C504.8 273.7 512 257.7 512 240c0-35.3-28.6-64-64-64l-92.3 0c4.7-10.4 8.7-21.2 11.8-32.2l5.7-20c10.9-38.2-11.2-78.1-49.4-89zM32 192c-17.7 0-32 14.3-32 32V448c0 17.7 14.3 32 32 32H96c17.7 0 32-14.3 32-32V224c0-17.7-14.3-32-32-32H32z"></path>
                    </svg>
                    <span class="dislike-text-content">4</span>
                  </label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
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
                        href="https://www.inflearn.com/users/14769/@weekendcode"
                    ><img
                        class="mantine-9rx0rd mantine-Avatar-image"
                        src="https://cdn.inflearn.com/public/users/thumbnails/14769/aa751976-0548-441b-9084-d71ff6327348?w=76"
                        alt="주말코딩님의 프로필 이미지"
                    /></a>
                    <div class="mantine-Stack-root mantine-1l47z8p">
                      <div class="mantine-824czz">
                        <a
                            class="mantine-Text-root mantine-3qdwx9"
                            href="https://www.inflearn.com/users/14769/@weekendcode"
                        >MR KIM</a
                        >
                        <div class="mantine-Badge-root mantine-11jjpd0">
                              <span class="mantine-1jlwn9k mantine-Badge-inner"
                              >답변자</span
                              >
                        </div>
                      </div>
                      <p class="mantine-Text-root mantine-1q4x896">
                        2024. 09. 04. 12:06
                      </p>
                    </div>
                  </div>
                </div>
                <div class="mantine-6va1do">
                  <div class="markdown-body mantine-0">
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
    <footer></footer>
  </div>
</template>

<script>
export default {
  name: "QuestionDetailComponent",
  data() {
    return {
      hello : "<h2 id=\"😲 md-editor-v3\" tabindex=\"-1\" data-line=\"0\"><a class=\"header-anchor\" href=\"#😲 md-editor-v3\">😲 md-editor-v3</a></h2> <p data-line=\"2\">Markdown Editor for Vue3, developed in jsx and typescript, support different themes、beautify content by prettier.</p> <h3 id=\"🤖 Base\" tabindex=\"-1\" data-line=\"4\"><a class=\"header-anchor\" href=\"#🤖 Base\">🤖 Base</a></h3> <p data-line=\"6\"><strong>bold</strong>, <u>underline</u>, <em>italic</em>, <s>line-through</s>, superscript<sup>26</sup>, subscript<sub>1</sub>, <code>inline code</code>, <a href=\"https://github.com/imzbf\" target=\"_blank\">link</a></p> <blockquote data-line=\"8\"> <p data-line=\"8\">quote: I Have a Dream</p> </blockquote> <ol data-line=\"10\"> <li data-line=\"10\">So even though we face the difficulties of today and tomorrow, I still have a dream.</li> <li data-line=\"11\">It is a dream deeply rooted in the American dream.</li> <li data-line=\"12\">I have a dream that one day this nation will rise up.</li> </ol> <ul class=\"contains-task-list\" data-line=\"14\"> <li class=\"task-list-item enabled\" data-line=\"14\"><input class=\"task-list-item-checkbox\" type=\"checkbox\"> Friday</li> <li class=\"task-list-item enabled\" data-line=\"15\"><input class=\"task-list-item-checkbox\" type=\"checkbox\"> Saturday</li> <li class=\"task-list-item enabled\" data-line=\"16\"><input class=\"task-list-item-checkbox\" checked type=\"checkbox\"> Sunday</li> </ul> <figure data-line=\"18\"><img src=\"https://imzbf.github.io/md-editor-rt/imgs/mark_emoji.gif\" alt=\"Picture\" class=\"md-zoom\"></figure> <h2 id=\"🤗 Code\" tabindex=\"-1\" data-line=\"20\"><a class=\"header-anchor\" href=\"#🤗 Code\">🤗 Code</a></h2> <details data-line=\"22\" class=\"md-editor-code\" open=\"\"> <summary class=\"md-editor-code-head\"> <div class=\"md-editor-code-flag\"><span></span><span></span><span></span></div> <div class=\"md-editor-code-action\"> <span class=\"md-editor-code-lang\">vue</span> <span class=\"md-editor-copy-button\" data-tips=\"Copy\"\">Copy</span> <span class=\"md-editor-collapse-tips\"><svg class=\"md-editor-icon\" aria-hidden=\"true\"><use xlink:href=\"#md-editor-icon-collapse-tips\"></use></svg></span> </div> </summary><pre><code class=\"language-vue\" language=vue><span class=\"md-editor-code-block\"><span class=\"language-xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">template</span>&gt;</span> <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">MdEditor</span> <span class=\"hljs-attr\">v-model</span>=<span class=\"hljs-string\">&quot;text&quot;</span> /&gt;</span> <span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">template</span>&gt;</span> <span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">script</span> <span class=\"hljs-attr\">setup</span>&gt;</span><span class=\"language-javascript\"> <span class=\"hljs-keyword\">import</span> { ref } <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">&#x27;vue&#x27;</span>; <span class=\"hljs-keyword\">import</span> { <span class=\"hljs-title class_\">MdEditor</span> } <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">&#x27;md-editor-v3&#x27;</span>; <span class=\"hljs-keyword\">import</span> <span class=\"hljs-string\">&#x27;md-editor-v3/lib/style.css&#x27;</span>; <span class=\"hljs-keyword\">const</span> text = <span class=\"hljs-title function_\">ref</span>(<span class=\"hljs-string\">&#x27;Hello Editor!&#x27;</span>); </span><span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">script</span>&gt;</span> </span></span><span rn-wrapper aria-hidden=\"true\"><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></span></code></pre> </details><h2 id=\"🖨 Text\" tabindex=\"-1\" data-line=\"36\"><a class=\"header-anchor\" href=\"#🖨 Text\">🖨 Text</a></h2> <p data-line=\"38\">The Old Man and the Sea served to reinvigorate Hemingway's literary reputation and prompted a reexamination of his entire body of work.</p> <h2 id=\"📈 Table\" tabindex=\"-1\" data-line=\"40\"><a class=\"header-anchor\" href=\"#📈 Table\">📈 Table</a></h2> <table data-line=\"42\"> <thead data-line=\"42\"> <tr data-line=\"42\"> <th style=\"text-align:left\">THead1</th> <th style=\"text-align:center\">THead2</th> <th style=\"text-align:right\">THead3</th> </tr> </thead> <tbody data-line=\"44\"> <tr data-line=\"44\"> <td style=\"text-align:left\">text-align:left</td> <td style=\"text-align:center\">text-align:center</td> <td style=\"text-align:right\">text-align:right</td> </tr> </tbody> </table> <h2 id=\"📏 Formula\" tabindex=\"-1\" data-line=\"46\"><a class=\"header-anchor\" href=\"#📏 Formula\">📏 Formula</a></h2> <p data-line=\"48\">Inline: <span class=\"md-editor-katex-inline\" data-processed><span class=\"katex\"><span class=\"katex-mathml\"><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><semantics><mrow><mi>x</mi><mo>+</mo><msup><mi>y</mi><mrow><mn>2</mn><mi>x</mi></mrow></msup></mrow><annotation encoding=\"application/x-tex\">x+y^{2x}</annotation></semantics></math></span><span class=\"katex-html\" aria-hidden=\"true\"><span class=\"base\"><span class=\"strut\" style=\"height:0.6667em;vertical-align:-0.0833em;\"></span><span class=\"mord mathnormal\">x</span><span class=\"mspace\" style=\"margin-right:0.2222em;\"></span><span class=\"mbin\">+</span><span class=\"mspace\" style=\"margin-right:0.2222em;\"></span></span><span class=\"base\"><span class=\"strut\" style=\"height:1.0085em;vertical-align:-0.1944em;\"></span><span class=\"mord\"><span class=\"mord mathnormal\" style=\"margin-right:0.03588em;\">y</span><span class=\"msupsub\"><span class=\"vlist-t\"><span class=\"vlist-r\"><span class=\"vlist\" style=\"height:0.8141em;\"><span style=\"top:-3.063em;margin-right:0.05em;\"><span class=\"pstrut\" style=\"height:2.7em;\"></span><span class=\"sizing reset-size6 size3 mtight\"><span class=\"mord mtight\"><span class=\"mord mtight\">2</span><span class=\"mord mathnormal mtight\">x</span></span></span></span></span></span></span></span></span></span></span></span></span></p> <p data-line=\"50\" class=\"md-editor-katex-block\" data-processed><span class=\"katex-display\"><span class=\"katex\"><span class=\"katex-mathml\"><math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\"><semantics><mrow><mroot><mi>x</mi><mn>3</mn></mroot></mrow><annotation encoding=\"application/x-tex\">\\sqrt[3]{x} </annotation></semantics></math></span><span class=\"katex-html\" aria-hidden=\"true\"><span class=\"base\"><span class=\"strut\" style=\"height:1.04em;vertical-align:-0.1908em;\"></span><span class=\"mord sqrt\"><span class=\"root\"><span class=\"vlist-t\"><span class=\"vlist-r\"><span class=\"vlist\" style=\"height:0.7172em;\"><span style=\"top:-2.895em;\"><span class=\"pstrut\" style=\"height:2.5em;\"></span><span class=\"sizing reset-size6 size1 mtight\"><span class=\"mord mtight\"><span class=\"mord mtight\">3</span></span></span></span></span></span></span></span><span class=\"vlist-t vlist-t2\"><span class=\"vlist-r\"><span class=\"vlist\" style=\"height:0.8492em;\"><span class=\"svg-align\" style=\"top:-3em;\"><span class=\"pstrut\" style=\"height:3em;\"></span><span class=\"mord\" style=\"padding-left:0.833em;\"><span class=\"mord mathnormal\">x</span></span></span><span style=\"top:-2.8092em;\"><span class=\"pstrut\" style=\"height:3em;\"></span><span class=\"hide-tail\" style=\"min-width:0.853em;height:1.08em;\"><svg xmlns=\"http://www.w3.org/2000/svg\" width='400em' height='1.08em' viewBox='0 0 400000 1080' preserveAspectRatio='xMinYMin slice'><path d='M95,702 c-2.7,0,-7.17,-2.7,-13.5,-8c-5.8,-5.3,-9.5,-10,-9.5,-14 c0,-2,0.3,-3.3,1,-4c1.3,-2.7,23.83,-20.7,67.5,-54 c44.2,-33.3,65.8,-50.3,66.5,-51c1.3,-1.3,3,-2,5,-2c4.7,0,8.7,3.3,12,10 s173,378,173,378c0.7,0,35.3,-71,104,-213c68.7,-142,137.5,-285,206.5,-429 c69,-144,104.5,-217.7,106.5,-221 l0 -0 c5.3,-9.3,12,-14,20,-14 H400000v40H845.2724 s-225.272,467,-225.272,467s-235,486,-235,486c-2.7,4.7,-9,7,-19,7 c-6,0,-10,-1,-12,-3s-194,-422,-194,-422s-65,47,-65,47z M834 80h400000v40h-400000z'/></svg></span></span></span><span class=\"vlist-s\">​</span></span><span class=\"vlist-r\"><span class=\"vlist\" style=\"height:0.1908em;\"><span></span></span></span></span></span></span></span></span></span></p><h2 id=\"🧬 Diagram\" tabindex=\"-1\" data-line=\"54\"><a class=\"header-anchor\" href=\"#🧬 Diagram\">🧬 Diagram</a></h2> <p class=\"md-editor-mermaid\" data-line=56 data-processed><svg aria-roledescription=\"flowchart-v2\" role=\"graphics-document document\" viewBox=\"-8 -8 66.01250076293945 132.80001068115234\" style=\"max-width: 66.01250076293945px;\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" width=\"100%\" id=\"m0vtbmasm0c185g8od7\"><style>#m0vtbmasm0c185g8od7{font-family:\"trebuchet ms\",verdana,arial,sans-serif;font-size:16px;fill:#333;}#m0vtbmasm0c185g8od7 .error-icon{fill:#552222;}#m0vtbmasm0c185g8od7 .error-text{fill:#552222;stroke:#552222;}#m0vtbmasm0c185g8od7 .edge-thickness-normal{stroke-width:2px;}#m0vtbmasm0c185g8od7 .edge-thickness-thick{stroke-width:3.5px;}#m0vtbmasm0c185g8od7 .edge-pattern-solid{stroke-dasharray:0;}#m0vtbmasm0c185g8od7 .edge-pattern-dashed{stroke-dasharray:3;}#m0vtbmasm0c185g8od7 .edge-pattern-dotted{stroke-dasharray:2;}#m0vtbmasm0c185g8od7 .marker{fill:#333333;stroke:#333333;}#m0vtbmasm0c185g8od7 .marker.cross{stroke:#333333;}#m0vtbmasm0c185g8od7 svg{font-family:\"trebuchet ms\",verdana,arial,sans-serif;font-size:16px;}#m0vtbmasm0c185g8od7 .label{font-family:\"trebuchet ms\",verdana,arial,sans-serif;color:#333;}#m0vtbmasm0c185g8od7 .cluster-label text{fill:#333;}#m0vtbmasm0c185g8od7 .cluster-label span,#m0vtbmasm0c185g8od7 p{color:#333;}#m0vtbmasm0c185g8od7 .label text,#m0vtbmasm0c185g8od7 span,#m0vtbmasm0c185g8od7 p{fill:#333;color:#333;}#m0vtbmasm0c185g8od7 .node rect,#m0vtbmasm0c185g8od7 .node circle,#m0vtbmasm0c185g8od7 .node ellipse,#m0vtbmasm0c185g8od7 .node polygon,#m0vtbmasm0c185g8od7 .node path{fill:#ECECFF;stroke:#9370DB;stroke-width:1px;}#m0vtbmasm0c185g8od7 .flowchart-label text{text-anchor:middle;}#m0vtbmasm0c185g8od7 .node .label{text-align:center;}#m0vtbmasm0c185g8od7 .node.clickable{cursor:pointer;}#m0vtbmasm0c185g8od7 .arrowheadPath{fill:#333333;}#m0vtbmasm0c185g8od7 .edgePath .path{stroke:#333333;stroke-width:2.0px;}#m0vtbmasm0c185g8od7 .flowchart-link{stroke:#333333;fill:none;}#m0vtbmasm0c185g8od7 .edgeLabel{background-color:#e8e8e8;text-align:center;}#m0vtbmasm0c185g8od7 .edgeLabel rect{opacity:0.5;background-color:#e8e8e8;fill:#e8e8e8;}#m0vtbmasm0c185g8od7 .labelBkg{background-color:rgba(232, 232, 232, 0.5);}#m0vtbmasm0c185g8od7 .cluster rect{fill:#ffffde;stroke:#aaaa33;stroke-width:1px;}#m0vtbmasm0c185g8od7 .cluster text{fill:#333;}#m0vtbmasm0c185g8od7 .cluster span,#m0vtbmasm0c185g8od7 p{color:#333;}#m0vtbmasm0c185g8od7 div.mermaidTooltip{position:absolute;text-align:center;max-width:200px;padding:2px;font-family:\"trebuchet ms\",verdana,arial,sans-serif;font-size:12px;background:hsl(80, 100%, 96.2745098039%);border:1px solid #aaaa33;border-radius:2px;pointer-events:none;z-index:100;}#m0vtbmasm0c185g8od7 .flowchartTitleText{text-anchor:middle;font-size:18px;fill:#333;}#m0vtbmasm0c185g8od7 :root{--mermaid-font-family:\"trebuchet ms\",verdana,arial,sans-serif;}</style><g><marker orient=\"auto\" markerHeight=\"12\" markerWidth=\"12\" markerUnits=\"userSpaceOnUse\" refY=\"5\" refX=\"6\" viewBox=\"0 0 10 10\" class=\"marker flowchart\" id=\"m0vtbmasm0c185g8od7_flowchart-pointEnd\"><path style=\"stroke-width: 1; stroke-dasharray: 1, 0;\" class=\"arrowMarkerPath\" d=\"M 0 0 L 10 5 L 0 10 z\"></path></marker><marker orient=\"auto\" markerHeight=\"12\" markerWidth=\"12\" markerUnits=\"userSpaceOnUse\" refY=\"5\" refX=\"4.5\" viewBox=\"0 0 10 10\" class=\"marker flowchart\" id=\"m0vtbmasm0c185g8od7_flowchart-pointStart\"><path style=\"stroke-width: 1; stroke-dasharray: 1, 0;\" class=\"arrowMarkerPath\" d=\"M 0 5 L 10 10 L 10 0 z\"></path></marker><marker orient=\"auto\" markerHeight=\"11\" markerWidth=\"11\" markerUnits=\"userSpaceOnUse\" refY=\"5\" refX=\"11\" viewBox=\"0 0 10 10\" class=\"marker flowchart\" id=\"m0vtbmasm0c185g8od7_flowchart-circleEnd\"><circle style=\"stroke-width: 1; stroke-dasharray: 1, 0;\" class=\"arrowMarkerPath\" r=\"5\" cy=\"5\" cx=\"5\"></circle></marker><marker orient=\"auto\" markerHeight=\"11\" markerWidth=\"11\" markerUnits=\"userSpaceOnUse\" refY=\"5\" refX=\"-1\" viewBox=\"0 0 10 10\" class=\"marker flowchart\" id=\"m0vtbmasm0c185g8od7_flowchart-circleStart\"><circle style=\"stroke-width: 1; stroke-dasharray: 1, 0;\" class=\"arrowMarkerPath\" r=\"5\" cy=\"5\" cx=\"5\"></circle></marker><marker orient=\"auto\" markerHeight=\"11\" markerWidth=\"11\" markerUnits=\"userSpaceOnUse\" refY=\"5.2\" refX=\"12\" viewBox=\"0 0 11 11\" class=\"marker cross flowchart\" id=\"m0vtbmasm0c185g8od7_flowchart-crossEnd\"><path style=\"stroke-width: 2; stroke-dasharray: 1, 0;\" class=\"arrowMarkerPath\" d=\"M 1,1 l 9,9 M 10,1 l -9,9\"></path></marker><marker orient=\"auto\" markerHeight=\"11\" markerWidth=\"11\" markerUnits=\"userSpaceOnUse\" refY=\"5.2\" refX=\"-1\" viewBox=\"0 0 11 11\" class=\"marker cross flowchart\" id=\"m0vtbmasm0c185g8od7_flowchart-crossStart\"><path style=\"stroke-width: 2; stroke-dasharray: 1, 0;\" class=\"arrowMarkerPath\" d=\"M 1,1 l 9,9 M 10,1 l -9,9\"></path></marker><g class=\"root\"><g class=\"clusters\"></g><g class=\"edgePaths\"><path marker-end=\"url(#m0vtbmasm0c185g8od7_flowchart-pointEnd)\" style=\"fill:none;\" class=\"edge-thickness-normal edge-pattern-solid flowchart-link LS-Start LE-Stop\" id=\"L-Start-Stop-0\" d=\"M25.006,33.4L25.006,37.567C25.006,41.733,25.006,50.067,25.006,57.517C25.006,64.967,25.006,71.533,25.006,74.817L25.006,78.1\"></path></g><g class=\"edgeLabels\"><g class=\"edgeLabel\"><g transform=\"translate(0, 0)\" class=\"label\"><foreignObject height=\"0\" width=\"0\"><div style=\"display: inline-block; white-space: nowrap;\" xmlns=\"http://www.w3.org/1999/xhtml\"><span class=\"edgeLabel\"></span></div></foreignObject></g></g></g><g class=\"nodes\"><g transform=\"translate(25.006250381469727, 16.700000762939453)\" id=\"flowchart-Start-2\" class=\"node default default flowchart-label\"><rect height=\"33.39999961853027\" width=\"50.01250076293945\" y=\"-16.699999809265137\" x=\"-25.006250381469727\" ry=\"0\" rx=\"0\" style=\"\" class=\"basic label-container\"></rect><g transform=\"translate(-17.506250381469727, -9.199999809265137)\" style=\"\" class=\"label\"><rect></rect><foreignObject height=\"18.399999618530273\" width=\"35.01250076293945\"><div style=\"display: inline-block; white-space: nowrap;\" xmlns=\"http://www.w3.org/1999/xhtml\"><span class=\"nodeLabel\">Start</span></div></foreignObject></g></g><g transform=\"translate(25.006250381469727, 100.10000228881836)\" id=\"flowchart-Stop-3\" class=\"node default default flowchart-label\"><rect height=\"33.39999961853027\" width=\"46.55000114440918\" y=\"-16.699999809265137\" x=\"-23.27500057220459\" ry=\"0\" rx=\"0\" style=\"\" class=\"basic label-container\"></rect><g transform=\"translate(-15.77500057220459, -9.199999809265137)\" style=\"\" class=\"label\"><rect></rect><foreignObject height=\"18.399999618530273\" width=\"31.55000114440918\"><div style=\"display: inline-block; white-space: nowrap;\" xmlns=\"http://www.w3.org/1999/xhtml\"><span class=\"nodeLabel\">Stop</span></div></foreignObject></g></g></g></g></g></svg></p><h2 id=\"🪄 Alert\" tabindex=\"-1\" data-line=\"61\"><a class=\"header-anchor\" href=\"#🪄 Alert\">🪄 Alert</a></h2> <div data-line=\"63\" class=\"md-editor-admonition md-editor-admonition-note\"> <p data-line=\"63\" class=\"md-editor-admonition-title\">Supported Types</p> <p data-line=\"65\">note、abstract、info、tip、success、question、warning、failure、danger、bug、example、quote、hint、caution、error、attention</p> </div> <h2 id=\"☘️ em...\" tabindex=\"-1\" data-line=\"69\"><a class=\"header-anchor\" href=\"#☘️ em...\">☘️ em...</a></h2>"
    };
  },
  props: ["qnaDetail"],
  methods: {},
  components: {},
};
</script>

<style>
.rounded-box {
  border-radius: 20px;
}
.circle {
  margin-left: 25px;
  margin-bottom: 10px;
  width: 120px;
  height: 120px;
  overflow: hidden;
  border-radius: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.circle img {
  width: 200%;
  height: auto;
}

img {
  max-width: 200%;
  height: auto;
}
/* 원형 이미지 */

.title-text {
  font-family: "auto";
  margin-left: 50px;
  font-size: 43px;
  font-weight: 900;
}

.username-text {
  margin-left: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: 900;
}

.datetime-text {
  margin-left: 10px;
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: 600;
}
.label-custom {
  margin-left: 20px;
}

.qna-background-color {
  background-color: #efefef;
}

/* like */
.like-dislike-container {
  --dark-grey: #353535;
  --middle-grey: #767676;
  --border-radius-icon: 50px;
  position: relative;
  display: flex;
  text-align: center;
  flex-direction: row;
  align-items: center;
  cursor: default;
  color: var(--dark-grey);
  opacity: 0.9;
  margin-left: auto;
  padding: 0rem;
  font-weight: 600;
  background: var(--lightest-grey);
  max-width: max-content;
  border-radius: var(--border-radius-main);
  box-shadow: var(--shadow);
  transition: 0.2s ease all;
}

.like-dislike-container:hover {
  box-shadow: var(--shadow-active);
}

.like-dislike-container .tool-box {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2.5rem;
  height: 2.5rem;
  top: 0;
  right: 0;
  border-radius: var(--border-radius-main);
}

.like-dislike-container .btn-close {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 0.8rem;
  height: 0.8rem;
  color: transparent;
  font-size: 0;
  cursor: pointer;
  background-color: #ff000080;
  border: none;
  border-radius: var(--border-radius-main);
  transition: 0.2s ease all;
}

.like-dislike-container .btn-close:hover {
  width: 1rem;
  height: 1rem;
  font-size: 1rem;
  color: #ffffff;
  background-color: #ff0000cc;
  box-shadow: var(--shadow-active);
}

.like-dislike-container .btn-close:active {
  width: 0.9rem;
  height: 0.9rem;
  font-size: 0.9rem;
  color: #ffffffde;
  --shadow-btn-close: 0 3px 3px 0 #00000026;
  box-shadow: var(--shadow-btn-close);
}

.like-dislike-container .text-content {
  margin-bottom: 1rem;
  font-size: 18px;
  line-height: 1.6;
  cursor: default;
}

.like-dislike-container .icons-box {
  display: flex;
}

.like-dislike-container .icons {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0.6;
  margin: 0 0.5rem;
  cursor: pointer;
  user-select: none;
  border: 1px solid var(--middle-grey);
  border-radius: var(--border-radius-icon);
  transition: 0.2s ease all;
}

.like-dislike-container .icons:hover {
  opacity: 0.9;
  box-shadow: var(--shadow);
}

.like-dislike-container .icons:active {
  opacity: 0.9;
  box-shadow: var(--shadow-active);
}

.like-dislike-container .icons .btn-label {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 0.5rem;
  cursor: pointer;
  position: relative;
}

.like-dislike-container .like-text-content {
  border-right: 0.1rem solid var(--dark-grey);
  padding: 0 0.6rem 0 0.5rem;
  pointer-events: none;
}

.like-dislike-container .dislike-text-content {
  border-left: 0.1rem solid var(--dark-grey);
  padding: 0 0.5rem 0 0.6rem;
  pointer-events: none;
}

.like-dislike-container .icons .svgs {
  width: 1.3rem;
  fill: #000000;
  box-sizing: content-box;
  padding: 10px 10px;
  transition: 0.2s ease all;
}

/* Hide the default checkbox */
.like-dislike-container .icons .input-box {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

.like-dislike-container .icons #icon-like-regular {
  display: block;
}

.like-dislike-container .icons #icon-like-solid {
  display: none;
}

.like-dislike-container .icons:hover :is(#icon-like-solid, #icon-like-regular) {
  animation: rotate-icon-like 0.7s ease-in-out both;
}

.like-dislike-container .icons #like-checkbox:checked ~ #icon-like-regular {
  display: none;
  animation: checked-icon-like 0.5s;
}

.like-dislike-container .icons #like-checkbox:checked ~ #icon-like-solid {
  display: block;
  animation: checked-icon-like 0.5s;
}

.like-dislike-container .icons #icon-dislike-regular {
  display: block;
  transform: rotate(180deg);
}

.like-dislike-container .icons #icon-dislike-solid {
  display: none;
  transform: rotate(180deg);
}

.like-dislike-container
.icons:hover
:is(#icon-dislike-solid, #icon-dislike-regular) {
  animation: rotate-icon-dislike 0.7s ease-in-out both;
}

.like-dislike-container
.icons
#dislike-checkbox:checked
~ #icon-dislike-regular {
  display: none;
  animation: checked-icon-dislike 0.5s;
}

.like-dislike-container .icons #dislike-checkbox:checked ~ #icon-dislike-solid {
  display: block;
  animation: checked-icon-dislike 0.5s;
}

.like-dislike-container .icons .fireworks {
  transform: scale(0.4);
}

.like-dislike-container
.icons
#like-checkbox:checked
~ .fireworks
> .checked-like-fx {
  position: absolute;
  width: 10px;
  height: 10px;
  right: 40px;
  border-radius: 50%;
  box-shadow: 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff;
  animation: 1s fireworks-bang ease-out forwards,
  1s fireworks-gravity ease-in forwards, 5s fireworks-position linear forwards;
  animation-duration: 1.25s, 1.25s, 6.25s;
}

.like-dislike-container
.icons
#dislike-checkbox:checked
~ .fireworks
> .checked-dislike-fx {
  position: absolute;
  width: 10px;
  height: 10px;
  left: 40px;
  border-radius: 50%;
  box-shadow: 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff,
  0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff, 0 0 #fff;
  animation: 1s fireworks-bang ease-out forwards,
  1s fireworks-gravity ease-in forwards, 5s fireworks-position linear forwards;
  animation-duration: 1.25s, 1.25s, 6.25s;
}

/* Shake Animation */
@keyframes rotate-icon-like {
  0% {
    transform: rotate(0deg) translate3d(0, 0, 0);
  }

  25% {
    transform: rotate(3deg) translate3d(0, 0, 0);
  }

  50% {
    transform: rotate(-3deg) translate3d(0, 0, 0);
  }

  75% {
    transform: rotate(1deg) translate3d(0, 0, 0);
  }

  100% {
    transform: rotate(0deg) translate3d(0, 0, 0);
  }
}

@keyframes rotate-icon-dislike {
  0% {
    transform: rotate(180deg) translate3d(0, 0, 0);
  }

  25% {
    transform: rotate(183deg) translate3d(0, 0, 0);
  }

  50% {
    transform: rotate(177deg) translate3d(0, 0, 0);
  }

  75% {
    transform: rotate(181deg) translate3d(0, 0, 0);
  }

  100% {
    transform: rotate(180deg) translate3d(0, 0, 0);
  }
}

/* Checked Animation */
@keyframes checked-icon-like {
  0% {
    transform: scale(0);
    opacity: 0;
  }

  50% {
    transform: scale(1.2) rotate(-10deg);
  }
}

@keyframes checked-icon-dislike {
  0% {
    transform: scale(0) rotate(180deg);
    opacity: 0;
  }

  50% {
    transform: scale(1.2) rotate(170deg);
  }
}

/* Fireworks Animation */
@keyframes fireworks-position {
  0%,
  19.9% {
    margin-top: 10%;
    margin-left: 40%;
  }

  20%,
  39.9% {
    margin-top: 40%;
    margin-left: 30%;
  }

  40%,
  59.9% {
    margin-top: 20%;
    margin-left: 70%;
  }

  60%,
  79.9% {
    margin-top: 30%;
    margin-left: 20%;
  }

  80%,
  99.9% {
    margin-top: 30%;
    margin-left: 80%;
  }
}

@keyframes fireworks-gravity {
  to {
    transform: translateY(200px);
    opacity: 0;
  }
}

@keyframes fireworks-bang {
  to {
    box-shadow: 114px -107.3333333333px #8800ff, 212px -166.3333333333px #a600ff,
    197px -6.3333333333px #ff006a, 179px -329.3333333333px #3300ff,
    -167px -262.3333333333px #ff0062, 233px 65.6666666667px #ff008c,
    81px 42.6666666667px #0051ff, -13px 54.6666666667px #00ff2b,
    -60px -183.3333333333px #0900ff, 127px -259.3333333333px #ff00e6,
    117px -122.3333333333px #00b7ff, 95px 20.6666666667px #ff8000,
    115px 1.6666666667px #0004ff, -160px -328.3333333333px #00ff40,
    69px -242.3333333333px #000dff, -208px -230.3333333333px #ff0400,
    30px -15.3333333333px #e6ff00, 235px -15.3333333333px #fb00ff,
    80px -232.3333333333px #d5ff00, 175px -173.3333333333px #00ff3c,
    -187px -176.3333333333px #aaff00, 4px 26.6666666667px #ff6f00,
    227px -106.3333333333px #ff0099, 119px 17.6666666667px #00ffd5,
    -102px 4.6666666667px #ff0088, -16px -4.3333333333px #00fff7,
    -201px -310.3333333333px #00ffdd, 64px -181.3333333333px #f700ff,
    -234px -15.3333333333px #00fffb, -184px -263.3333333333px #aa00ff,
    96px -303.3333333333px #0037ff, -139px 10.6666666667px #0026ff,
    25px -205.3333333333px #00ff2b, -129px -322.3333333333px #40ff00,
    -235px -187.3333333333px #26ff00, -136px -237.3333333333px #0091ff,
    -82px -321.3333333333px #6a00ff, 7px -267.3333333333px #ff00c8,
    -155px 30.6666666667px #0059ff, -85px -73.3333333333px #6a00ff,
    60px -199.3333333333px #55ff00, -9px -289.3333333333px #00ffaa,
    -208px -167.3333333333px #00ff80, -13px -299.3333333333px #ff0004,
    179px -164.3333333333px #ff0044, -112px 12.6666666667px #0051ff,
    -209px -125.3333333333px #ff00bb, 14px -101.3333333333px #00ff95,
    -184px -292.3333333333px #ff0099, -26px -168.3333333333px #09ff00,
    129px -67.3333333333px #0084ff, -17px -23.3333333333px #0059ff,
    129px 34.6666666667px #7300ff, 35px -24.3333333333px #ffd900,
    -12px -297.3333333333px #ff8400, 129px -156.3333333333px #0dff00,
    157px -29.3333333333px #1a00ff, -221px 6.6666666667px #ff0062,
    0px -311.3333333333px #ff006a, 155px 50.6666666667px #00ffaa,
    -71px -318.3333333333px #0073ff;
  }
}
/* like */

/* book mark */
.bookmark-checkbox {
  display: inline-block;
}

.bookmark-checkbox__input {
  display: none;
}

.bookmark-checkbox__label {
  cursor: pointer;
}

.bookmark-checkbox__icon {
  margin-left: 20px;
  width: 2em;
  height: 2em;
  fill: none;
  stroke-width: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
  transition: stroke 0.3s, fill 0.3s;
}

.bookmark-checkbox__icon-back {
  stroke: #333;
  transition: transform 0.3s;
}

.bookmark-checkbox__icon-check {
  stroke: #fff;
  stroke-dasharray: 16;
  stroke-dashoffset: 16;
  transition: stroke-dashoffset 0.3s, transform 0.3s;
  transform: translateX(2px);
}

.bookmark-checkbox__input:checked
+ .bookmark-checkbox__label
.bookmark-checkbox__icon {
  fill: #ff5a5f;
}

.bookmark-checkbox__input:checked
+ .bookmark-checkbox__label
.bookmark-checkbox__icon-back {
  stroke: #ff5a5f;
  transform: scale(1.1);
  animation: bookmark-pop 0.4s ease;
}

.bookmark-checkbox__input:checked
+ .bookmark-checkbox__label
.bookmark-checkbox__icon-check {
  stroke-dashoffset: 0;
  transition-delay: 0.2s;
}

@keyframes bookmark-pop {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1.1);
  }
}
/* book mark */

element.style {
  background: #fff;
  border: 1px solid #19192c;
}

.bg-black.text-white.p-6.rounded-lg.w-full.max-w-md.font-mono {
  max-width: 70rem;
  margin: auto;
}

/* 댓글 */
.mantine-9rx0rd {
  object-fit: cover;
  width: 100%;
  height: 100%;
  display: block;
}
.mantine-18l6s08 {
  -webkit-tap-highlight-color: transparent;
  box-sizing: border-box;
  position: relative;
  display: block;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  overflow: hidden;
  border-radius: 200%;
  border-top-left-radius: 200%;
  border-top-right-radius: 200%;
  border-bottom-right-radius: 200%;
  border-bottom-left-radius: 200%;
  -webkit-text-decoration: none;
  text-decoration: none;
  border: 0;
  background-color: transparent;
  padding: 0;
  width: 5rem;
  height: 5rem;
  border: 1px solid #dee2e6;
}
.mantine-18l6s09 {
  -webkit-tap-highlight-color: transparent;
  box-sizing: border-box;
  position: relative;
  display: block;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  overflow: hidden;
  border-radius: 200%;
  border-top-left-radius: 200%;
  border-top-right-radius: 200%;
  border-bottom-right-radius: 200%;
  border-bottom-left-radius: 200%;
  -webkit-text-decoration: none;
  text-decoration: none;
  border: 0;
  background-color: transparent;
  padding: 0;
  width: 5rem;
  height: 5rem;
  border: 1px solid #dee2e6;
  justify-content: center;
  align-items: center;
}

.mantine-9p81l6 {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  color: #495057;
  font-size: 0.75rem;
  line-height: 1.5;
  -webkit-text-decoration: none;
  text-decoration: none;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  line-height: 1.5;
  text-underline-position: under;
}
.mantine-9p81l6:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}
.mantine-9p81l6:focus:not(:focus-visible) {
  outline: 0;
}
.mantine-1uguyhf {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  gap: 0.5rem;
}
.mantine-1q4x896 {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  color: #868e96;
  font-size: 0.875rem;
  line-height: 1.5;
  -webkit-text-decoration: none;
  text-decoration: none;
  line-height: 1.5;
  text-underline-position: under;
}
.mantine-1q4x896:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}
.mantine-1q4x896:focus:not(:focus-visible) {
  outline: 0;
}
.mantine-1yjkc96 {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  height: 100%;
  overflow: visible;
  pointer-events: none;
}
.mantine-1ryt1ht {
  white-space: nowrap;
  height: 100%;
  overflow: hidden;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}
.mantine-19ok7fu {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  margin-right: 0.625rem;
  margin-right: 6px;
}
.mantine-19ok7fu svg {
  width: 14px;
  height: 14px;
}
.mantine-k7c4r8 {
  -webkit-tap-highlight-color: transparent;
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  cursor: pointer;
  border: 0;
  padding: 0;
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  appearance: none;
  font-size: 1rem;
  background-color: transparent;
  text-align: left;
  color: #000;
  -webkit-text-decoration: none;
  text-decoration: none;
  box-sizing: border-box;
  height: 2.25rem;
  padding-left: calc(1.125rem / 1.5);
  padding-right: 1.125rem;
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  display: inline-block;
  width: auto;
  border-radius: 2rem;
  font-weight: 600;
  position: relative;
  line-height: 1;
  font-size: 0.875rem;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  cursor: pointer;
  border: 0.0625rem solid #ced4da;
  background-color: #fff;
  color: #000;
  color: #212529;
  padding-left: 18px;
}
.mantine-k7c4r8:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}
.mantine-k7c4r8:focus:not(:focus-visible) {
  outline: 0;
}
.mantine-k7c4r8:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}
.mantine-k7c4r8:focus:not(:focus-visible) {
  outline: 0;
}
@media (hover: hover) {
  .mantine-k7c4r8:hover {
    background-color: #f8f9fa;
  }
}
@media (hover: none) {
  .mantine-k7c4r8:active {
    background-color: #f8f9fa;
  }
}
.mantine-k7c4r8:active {
  -webkit-transform: translateY(0.0625rem);
  -moz-transform: translateY(0.0625rem);
  -ms-transform: translateY(0.0625rem);
  transform: translateY(0.0625rem);
}
.mantine-k7c4r8:disabled,
.mantine-k7c4r8[data-disabled] {
  border-color: transparent;
  background-color: #e9ecef;
  color: #adb5bd;
  cursor: not-allowed;
  background-image: none;
  pointer-events: none;
}
.mantine-k7c4r8:disabled:active,
.mantine-k7c4r8[data-disabled]:active {
  -webkit-transform: none;
  -moz-transform: none;
  -ms-transform: none;
  transform: none;
}
.mantine-k7c4r8[data-loading] {
  pointer-events: none;
}
.mantine-k7c4r8[data-loading]::before {
  content: "";
  position: absolute;
  top: -0.0625rem;
  right: -0.0625rem;
  left: -0.0625rem;
  bottom: -0.0625rem;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 2rem;
  cursor: not-allowed;
}
.mantine-1qj7q0z {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  width: 2.25rem;
  pointer-events: none;
}
.mantine-1ttseea {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-direction: column;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-align-items: stretch;
  -webkit-box-align: stretch;
  -ms-flex-align: stretch;
  align-items: stretch;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  -webkit-justify-content: flex-start;
  justify-content: flex-start;
  gap: 0;
  width: 100%;
  min-width: 0;
}
.mantine-2j9uwr {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  height: 30px;
  -webkit-box-pack: justify;
  -webkit-justify-content: space-between;
  justify-content: space-between;
}
.mantine-18l6s08 {
  -webkit-tap-highlight-color: transparent;
  box-sizing: border-box;
  position: relative;
  display: block;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  overflow: hidden;
  border-radius: 2rem;
  -webkit-text-decoration: none;
  text-decoration: none;
  border: 0;
  background-color: transparent;
  padding: 0;
  width: 2.375rem;
  min-width: 2.375rem;
  height: 2.375rem;
  border: 1px solid #dee2e6;
}
.mantine-18l6s08:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}
.mantine-18l6s08:focus:not(:focus-visible) {
  outline: 0;
}
.mantine-1l47z8p {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-direction: column;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-align-items: stretch;
  -webkit-box-align: stretch;
  -ms-flex-align: stretch;
  align-items: stretch;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  gap: 0;
}
.mantine-824czz {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  gap: 0.25rem;
}
.mantine-3qdwx9 {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  -webkit-tap-highlight-color: transparent;
  color: #495057;
  font-size: 0.875rem;
  line-height: 1.5;
  -webkit-text-decoration: none;
  text-decoration: none;
  font-weight: 700;
  line-height: 1.5;
  text-underline-position: under;
}
.mantine-3qdwx9:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}
.mantine-3qdwx9:focus:not(:focus-visible) {
  outline: 0;
}
.mantine-1jlwn9k {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 100%;
  letter-spacing: normal;
}
.mantine-11jjpd0 {
  -webkit-tap-highlight-color: transparent;
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
  "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
  "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
  sans-serif;
  font-size: 0.6875rem;
  height: 25px;
  line-height: calc(1.25rem - 0.125rem);
  -webkit-text-decoration: none;
  text-decoration: none;
  padding: 0 calc(1rem / 1.5);
  box-sizing: border-box;
  display: -webkit-inline-box;
  display: -webkit-inline-flex;
  display: -ms-inline-flexbox;
  display: inline-flex;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  -webkit-justify-content: center;
  justify-content: center;
  width: auto;
  text-transform: uppercase;
  border-radius: 0.25rem;
  font-weight: 700;
  letter-spacing: 0.015625rem;
  cursor: inherit;
  text-overflow: ellipsis;
  overflow: hidden;
  background: rgba(229, 249, 241, 1);
  color: #00894f;
  border: 0.0625rem solid transparent;
  text-transform: none;
  padding: 0 8px;
}
.mantine-11jjpd0:focus {
  outline-offset: 0.125rem;
  outline: 0.125rem solid #212529;
}
.mantine-11jjpd0:focus:not(:focus-visible) {
  outline: 0;
}
.mantine-6va1do {
  margin-top: 0.5rem;
}
.mantine-12kvhdu {
  margin-top: 0.75rem;
}
.mantine-1qj7haw {
  box-sizing: border-box;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-direction: row;
  -ms-flex-direction: row;
  flex-direction: row;
  -webkit-align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-flex-wrap: wrap;
  -webkit-flex-wrap: wrap;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  -webkit-justify-content: flex-start;
  justify-content: flex-start;
  gap: 0.75rem;
}
.mantine-1qj7haw > * {
  box-sizing: border-box;
  -webkit-box-flex: 0;
  -webkit-flex-grow: 0;
  -ms-flex-positive: 0;
  flex-grow: 0;
}
.mantine-17p85na {
  padding-left: 28px;
  position: relative;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  gap: 0.75rem;
}
.mantine-17p85na:not(.mantine-17p85na:first-of-type) .re-comment-arrow {
  padding-top: 36px;
}
.mantine-17p85na:not(.mantine-17p85na:first-of-type) .re-comment {
  padding-top: 24px;
  border-top: 1px solid #e9ecef;
}
.mantine-155ln8z {
  left: 0;
  font-size: 0;
  position: absolute;
  padding-top: 0.75rem;
  padding-bottom: 0.75rem;
}
.mantine-5n4x4z {
  width: 100%;
}
.mantine-11k5015 {
  border-radius: 16px;
  border: 1px solid #e9ecef;
  padding: 1.5rem;
}
h1 {
  font-size: 2em;
}
pre {
  font-family: monospace, monospace;
  font-size: 1em;
}
a {
  background: 0 0;
  text-decoration-skip: objects;
}
a:active,
a:hover {
  outline-width: 0;
}
b {
  font-weight: bolder;
}
code {
  font-family: monospace, monospace;
  font-size: 1em;
}
img {
  border-style: none;
  vertical-align: middle;
}
svg:not(:root) {
  overflow: hidden;
}
button {
  font-family: sans-serif;
  font-size: 100%;
  line-height: 1.15;
  margin: 0;
}
button {
  overflow: visible;
}
button {
  text-transform: none;
}
[type="reset"],
[type="submit"],
button {
  -webkit-appearance: button;
}
[type="button"]::-moz-focus-inner,
[type="reset"]::-moz-focus-inner,
[type="submit"]::-moz-focus-inner,
button::-moz-focus-inner {
  border-style: none;
  padding: 0;
}
[type="button"]:-moz-focusring,
[type="reset"]:-moz-focusring,
[type="submit"]:-moz-focusring,
button:-moz-focusring {
  outline: 0.0625rem dotted ButtonText;
}
[type="checkbox"],
[type="radio"] {
  box-sizing: border-box;
  padding: 0;
}
[type="number"]::-webkit-inner-spin-button,
[type="number"]::-webkit-outer-spin-button {
  height: auto;
}
[type="search"] {
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  appearance: none;
}
[type="search"]::-webkit-search-cancel-button,
[type="search"]::-webkit-search-decoration {
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  appearance: none;
}
::-webkit-file-upload-button {
  -webkit-appearance: button;
  -moz-appearance: button;
  -ms-appearance: button;
  appearance: button;
  font: inherit;
}
::after,
::before {
  box-sizing: border-box;
}
html {
  -webkit-print-color-scheme: light;
  color-scheme: light;
}
a,
b,
code,
div,
h1,
h2,
h3,
h4,
h5,
h6,
img,
label,
p,
pre,
q,
s,
span {
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 17px;
  vertical-align: baseline;
}
q {
  quotes: none;
}
q:after,
q:before {
  content: none;
}

.answer-name-text {
  font-size: 20px;
}
/* 댓글 */

.red-button-size {
  height: 35px;
}

.question-answer {
  font-size: 30px;
  font-weight: 900;
}

#answer-title-text {
  margin-top: 4rem;
}

#user-profile-image {
  max-width: 200%;
}
</style>
