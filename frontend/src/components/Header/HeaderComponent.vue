<template>
    <header>
        <div class="logo">
            <router-link :to="{ path: '/' }"><h1>Enadu</h1></router-link>
        </div>
        <nav class="navigation">
            <ul>
                <li><router-link :to="{ path: '/' }"><i class="fas fa-code"></i> 아카이브</router-link></li>
                <li class="divider">|</li>
                <li><router-link :to="{ path: '/' }"><i class="fas fa-book"></i> 위키</router-link></li>
                <li class="divider">|</li>
                <li>
                    <router-link :to="{ path: '/qna/list' }"><i class="fas fa-question-circle"></i> QnA</router-link>
                </li>
            </ul>
        </nav>
        <div class="search-bar">
            <input type="text" placeholder="검색어를 입력하세요">
            <button @click="showAlert"><i class="fas fa-search"></i></button>
        </div>
        <div class="auth-navigation">
            <div v-if="!isLoggedIn">
                <ul>
                    <li>
                        <router-link :to="{ path: '/login', query: { mode: 'login' } }">로그인</router-link>
                    </li>
                    <li class="divider">|</li>
                    <li>
                        <router-link :to="{ path: '/login', query: { mode: 'signup' } }">회원가입</router-link>
                    </li>
                </ul>
            </div>
            <div v-else>
                <ul>
                    <li><a href="#" @click.prevent="logout">로그아웃</a></li>
                    <li class="divider">|</li>
                    <li class="dropdown">
                        <router-link :to="{ path: '/mypage/info' }"><i class="fas fa-caret-down"></i> 마이페이지</router-link>
                        <ul class="dropdown-menu">
                            <li><router-link :to="{ path: '/mypage/info' }"><i class="fas fa-user"></i> 회원 정보</router-link></li>
                            <li><router-link :to="{ path: '/mypage/history' }"><i class="fas fa-file-alt"></i> 작성 내역</router-link></li>
                            <li><router-link :to="{ path: '/mypage/scrap' }"><i class="fas fa-bookmark"></i> 스크랩 내역</router-link></li>
                            <li><router-link :to="{ path: '/point/info' }"><i class="fas fa-coins"></i> 포인트 및 랭킹</router-link></li>
                            <li><router-link :to="{ path: '/chat' }"><i class="fas fa-comments"></i> 채팅</router-link></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </header>
</template>

<script>
import {mapStores} from "pinia";
import {useUserStore} from "@/store/useUserStore";

export default {
    name: "HeaderComponent",
    computed: {
        ...mapStores(useUserStore),
        isLoggedIn() {
            return this.userStore.isLoggedIn;
        },
    },
    methods: {
        showAlert() {
            alert("통합 검색은 추후 추가 예정입니다.")
        },
        logout() {
            if (window.confirm("로그아웃 하시겠습니까?")) {
                this.userStore.logout();
                alert("로그아웃 되었습니다.")
            }
        }
    }
}
</script>

<style scoped lang="scss">
header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #fff;
    padding: 15px 30px;
    border-bottom: 2px solid #ddd;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

i {
    padding-right: 10px;
    font-size: 18px;
}

.logo {
    margin-right: 50px;
}

.logo h1 {
    font-size: 28px;
    font-weight: bold;
    color: #333;
}

.navigation {
    flex-grow: 1;
}

.navigation ul, .auth-navigation ul {
    list-style: none;
    display: flex;
    gap: 10px;
    align-items: center;
}

.navigation ul li, .auth-navigation ul li {
    display: flex;
    align-items: center;
}

.navigation ul li.divider, .auth-navigation ul li.divider {
    font-size: 20px;
    color: #ccc;
}

.navigation ul li a, .auth-navigation ul li a {
    text-decoration: none;
    color: #333;
    font-size: 15px;
    display: flex;
    align-items: center;
    gap: 5px;
    padding: 5px;
}

.navigation ul li a:hover, .auth-navigation ul li a:hover {
    color: var(--main-color);
}

.auth-navigation ul li.dropdown {
    position: relative;
}

.auth-navigation ul li.dropdown .dropdown-menu {
    display: none;
    position: absolute;
    top: 100%;
    right: -10%;
    background-color: #fff;
    border: 1px solid #ddd;
    z-index: 1000;
    list-style: none;
    width: 170px;
}

.auth-navigation ul li.dropdown .dropdown-menu li {
    padding: 10px 15px;
}

.auth-navigation ul li.dropdown .dropdown-menu li a {
    color: #333;
    text-decoration: none;
    display: block;
}

.auth-navigation ul li.dropdown .dropdown-menu li a:hover {
    color: var(--main-color);
}

.auth-navigation ul li.dropdown:hover .dropdown-menu {
    display: block;
}

.search-bar {
    display: flex;
    align-items: center;
    background-color: #fafcfc;
    padding: 8px;
    border-radius: 25px;
    box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-left: auto;
    margin-right: 30px;
    border: 1px solid var(--main-color);
}

.search-bar input {
    border: none;
    outline: none;
    background: none;
    padding: 7px;
    width: 300px;
    font-size: 16px;
    height: 32.5px !important;
}

.search-bar button {
    background-color: #fafcfc;
    border: none;
    border-radius: 7px;
    width: 30px;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    color: white;
}

.search-bar i {
    color: var(--main-color);
}

@media (max-width: 768px) {
    header {
        flex-direction: column;
        align-items: flex-start;
    }

    .navigation ul, .auth-navigation ul {
        flex-direction: column;
        gap: 10px;
    }

    .search-bar {
        width: 100%;
        margin-top: 10px;
    }
}
</style>