<template>
    <div class="overlay-container" :class="{ active: !signIn }">
        <div class="overlay" :class="{ active: !signIn }">
            <div class="left-overlay-panel" :class="{ 'is-visible': !signIn }" v-show="true">
                <h1 class="title">에나두랑 함께하기</h1>
                <p class="paragraph">
                    개발자를 위한 통합 플랫폼 에나두는 <br>개발자에게 어쩌구 저쩌구한 경험을 줄 수 있습니다.
                </p>
                <button class="ghost-button" @click="$emit('toggleSignIn', true)">로그인</button>
            </div>
            <div class="right-overlay-panel" :class="{ 'is-visible': signIn }" v-show="true">
                <h1 class="title">모든 오류를 모아서 <br/>한 곳에, <i>에나두</i></h1>
                <p class="paragraph">에나두 계정이 없으신가요?</p>
                <button class="ghost-button" @click="$emit('toggleSignIn', false)">회원가입</button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "OverlayComponent",
        props: {
            signIn: Boolean,
        },
    };
</script>

<style scoped>
.left-overlay-panel, .right-overlay-panel {
    opacity: 0;
    transition: opacity 0.5s ease, visibility 0.5s ease;
    visibility: hidden;
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
}
.is-visible {
    opacity: 1;
    visibility: visible;
}
.overlay-container {
    position: absolute;
    top: 0;
    left: 50%;
    width: 50%;
    height: 100%;
    overflow: hidden;
    transition: transform 0.6s ease-in-out;
    z-index: 100;
}
.overlay-container.active {
    transform: translateX(-100%);
}

.overlay {
  background: linear-gradient(-20deg, var(--main-color) 0%, #42a5f5 100%);
  /* background: var(--main-color); */
  background-repeat: no-repeat;
  background-size: cover;
  color: #ffffff;
  position: relative;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: transform 0.6s ease-in-out;
}

.title {
  font-size: 25px;
  font-weight: 600;
  margin: 10px;
  margin-bottom: 15px;
}

.paragraph {
  font-size: 18px;
  font-weight: 400;
  line-height: 25px;
  margin: 20px 0 30px;
  padding: 20px;
}

button {
  border-radius: 20px;
  border: 1px solid var(--main-color);
  background-color: var(--main-color);
  color: #ffffff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  filter: drop-shadow(4px 4px 8px rgba(0, 0, 0, 0.25));
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

button:active {
  transform: scale(0.95);
}

button:focus {
  outline: none;
}

.ghost-button {
  background-color: transparent;
  border-color: #ffffff;
}

</style>