<template>
    <div class="overflow-hidden rounded-lg border border-gray-500 border-opacity-30">
        <div class="mx-5 mb-5 mt-6 sm:mx-6">
            <div class="flex items-center justify-center">
                <img class="h-10 w-10 rounded-full border border-gray-500/30 sm:h-16 sm:w-16" alt="프로필 사진"
                     :src="mypageStore.userInfo.profileImg">
                <div class="ml-3.5 mr-2 flex flex-1 flex-col sm:ml-5 sm:text-2xl">
                    <div class="flex items-center gap-x-2">
                        <span class="line-clamp-1 w-fit break-all pl-0.5 font-bold">{{ mypageStore.userInfo.nickname }}</span>
                        <div class="flex items-center text-sm sm:text-base">
                            <span :class="dynamicClass">{{ mypageStore.userInfo.grade }}</span>
                        </div>
                    </div>
                </div>
                <router-link :to="{ path: '/mypage/history' }"
                             class="inline-flex justify-center rounded-md border border-white-500/30 bg-white px-4 py-2 text-sm font-medium text-black-700 hover:border-white-500/70 focus:outline-none dark:bg-white-700 dark:text-black-300 mr-2">
                    작성 내역
                </router-link>
                <router-link :to="{ path: '/mypage/scrap' }"
                             class="inline-flex justify-center rounded-md border border-white-500/30 bg-white px-4 py-2 text-sm font-medium text-black-700 hover:border-white-500/70 focus:outline-none dark:bg-white-700 dark:text-black-300">
                    스크랩 목록
                </router-link>
            </div>
        </div>
    </div>
    <div class="my-4 lg:my-0 px-6 max-w-4xl mx-auto">
        <div class="my-4 flex flex-col lg:flex-row space-y-4 lg:space-y-0 lg:space-x-4">
            <div class="flex-grow space-y-4">
                <div class="block text-xl font-medium text-gray-700 dark:text-gray-300">
                    이메일
                    <div class="mt-2 flex">
                        <span id="username"
                              class="block w-1/2 rounded-md border border-gray-500/30 px-3 py-2 text-sm shadow-sm sm:text-base cursor-not-allowed">
                            {{ mypageStore.userInfo.email }}
                        </span>
                    </div>
                </div>
                <div v-show="!mypageStore.userInfo.isSocialUser" class="block mt-4 text-xl">
                    비밀번호
                    <div class="mt-2 flex">
                        <button
                            class="w-full sm:w-auto bg-gray-600 hover:bg-gray-700 text-white text-sm py-2 px-4 rounded-lg transition-all"
                            @click="togglePasswordModal">
                            비밀번호 변경
                        </button>
                    </div>
                </div>
                <div class="block text-xl font-medium text-gray-700 dark:text-gray-300">
                    닉네임
                    <div class="mt-2 flex items-center">
                        <input type="text" id="nickname" autocomplete="nickname"
                               class="block w-1/2 appearance-none rounded-md border border-gray-500/30 px-3 py-2 text-sm placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 sm:text-base dark:bg-gray-500/20"
                               :disabled="!isNicknameEditable" v-model="nickname">
                        <button type="button"
                                class="ml-4 text-white bg-blue-600 hover:bg-blue-700 font-medium rounded-lg text-sm px-4 py-2"
                                @click="toggleNicknameEdit">
                            {{ isNicknameEditable ? '중복 확인 및 변경' : '닉네임 수정' }}
                        </button>
                    </div>
                </div>

                <div class="block text-xl font-medium text-gray-700 dark:text-gray-300">소셜 회원
                    <div class="mt-2 flex">
                        <span id="username"
                              class="rounded-md border border-gray-500/30 px-3 py-2 text-sm shadow-sm sm:text-base w-sm cursor-default">
                            {{ mypageStore.userInfo.isSocialUser }}
                        </span>
                    </div>
                </div>
                <div class="quit_button mt-4">
                    <div class="quit_button_text" @click="confirmQuit">
                        회원 탈퇴
                    </div>
                </div>
            </div>

            <div class="flex-grow lg:mt-0 lg:shrink-0 lg:grow-0">
                <div class="mt-6">
                    <p class="text-xl font-medium text-gray-700 dark:text-gray-300 text-center mb-4" aria-hidden="true">
                        프로필 사진
                    </p>
                    <div class="relative" data-headlessui-state="">
                        <div class="relative my-1 hidden overflow-hidden rounded-full lg:block">
                            <img class="h-40 w-40 rounded-full border-2 border-gray-200"
                                 :src="mypageStore.userInfo.profileImg"
                                 alt="프로필 사진">
                        </div>
                        <input type="file" @change="handleFileChange" class="hidden" ref="fileInput">
                        <button type="button"
                                class="text-white bg-gray-600 hover:bg-gray-700 font-medium rounded-lg text-sm px-4 py-2 mt-4 w-full"
                                @click="triggerFileInput">
                            이미지 등록
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <PasswordModal v-show="showPasswordModal" @close="togglePasswordModal"/>
    </div>
</template>

<script>
import { useMypageStore } from "@/store/useMypageStore";
import { useUserStore } from "@/store/useUserStore";
import { mapStores } from "pinia";
import PasswordModal from "@/components/Mypage/Info/PasswordModal.vue";

export default {
    name: "MyInfoComponent",
    components: {PasswordModal},
    data() {
        return {
            isNicknameEditable: false,
            nickname: "",
            selectedFile: null,
            showPasswordModal: false,
        };
    },
    computed: {
        ...mapStores(useMypageStore, useUserStore),
        dynamicClass() {
            switch (this.mypageStore.userInfo.grade) {
                case '뉴비':
                    return 'newbie';
                case '견습':
                    return 'apprentice';
                case '프로':
                    return 'pro';
                case '마스터':
                    return 'master';
                case '신':
                    return 'god';
                default:
                    return '';
            }
        }
    },
    mounted() {
        this.$emit("sub-title", "회원 정보 조회");
        this.mypageStore.fetchUserInfo();
        this.nickname = this.mypageStore.userInfo.nickname;
    },
    methods: {
        async toggleNicknameEdit() {
            if (this.isNicknameEditable) {
                await this.checkNicknameDuplicate();
            } else {
                this.isNicknameEditable = true;
            }
        },
        togglePasswordModal() {
            this.showPasswordModal = !this.showPasswordModal;
        },
        async checkNicknameDuplicate() {
            try {
                const isAvailable = await this.mypageStore.checkNickname(this.nickname);
                if (isAvailable) {
                    if (window.confirm("닉네임을 사용할 수 있습니다. 변경 하시겠습니까?")) {
                        await this.updateNickname();
                    }
                } else {
                    alert("이미 사용 중인 닉네임입니다.");
                }
            } catch (error) {
                alert("닉네임 중복 확인 중 오류가 발생했습니다.");
            }
        },
        async updateNickname() {
            try {
                const updateSuccess = await this.mypageStore.updateNickname(this.nickname);
                if (updateSuccess) {
                    alert("닉네임을 성공적으로 변경하였습니다.");
                    this.isNicknameEditable = false;
                } else {
                    alert("닉네임 변경에 실패하였습니다.");
                }
            } catch (error) {
                alert("닉네임 업데이트 중 오류가 발생했습니다.");
            }
        },
        triggerFileInput() {
            this.$refs.fileInput.click();
        },
        handleFileChange(event) {
            const file = event.target.files[0];
            const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

            if (file && !allowedTypes.includes(file.type)) {
                alert('지원하지 않는 파일 형식입니다. jpg, png 또는 gif 파일을 선택해주세요.');
                return;
            }

            this.selectedFile = file;
            this.uploadProfileImage();
        },
        async uploadProfileImage() {
            if (!this.selectedFile) {
                alert("파일이 선택되지 않았습니다.");
                return;
            }
            const formData = new FormData();
            formData.append('imgFile', this.selectedFile);
            formData.forEach((value, key) => {
                console.log(`${key}:`, value);
            });
            try {
                const response = await this.mypageStore.uploadProfileImage(formData);
                if (response) {
                    alert("이미지가 성공적으로 업로드되었습니다.");
                }
            } catch (error) {
                alert("이미지 업로드 중 오류가 발생했습니다.");
            }
        },
        confirmQuit() {
            if (window.confirm("정말로 회원 탈퇴를 하시겠습니까?")) {
                if (this.mypageStore.userInfo.isSocialUser) {
                    this.socialQuitAccount();
                } else {
                    this.quitAccount();
                }
            }
        },
        async quitAccount() {
            // 비밀번호 입력 요청
            const password = prompt("회원 탈퇴를 위해 비밀번호를 입력해주세요.");
            if (!password) {
                alert("비밀번호를 입력해주세요.");
                return;
            }
            try {
                const quitSuccess = await this.userStore.quitAccount(password);
                if (quitSuccess) {
                    alert("회원 탈퇴가 완료되었습니다.");
                    this.$router.push("/").then(() => {
                        window.location.reload();
                    });
                } else {
                    alert("회원 탈퇴에 실패하였습니다.");
                }
            } catch (error) {
                alert("회원 탈퇴 중 오류가 발생했습니다.");
            }
        },
        async socialQuitAccount() {
            try {
                const quitSuccess = await this.userStore.socialQuit();
                if (quitSuccess) {
                    alert("회원 탈퇴가 완료되었습니다.");
                    this.$router.push("/").then(() => {
                        window.location.reload();
                    });
                } else {
                    alert("회원 탈퇴에 실패하였습니다.");
                }
            } catch (error) {
                alert("회원 탈퇴 중 오류가 발생했습니다.");
            }
        }
    },
    watch: {
        'mypageStore.userInfo.nickname'(newNickname) {
            this.nickname = newNickname;
        }
    }
}
</script>

<style scoped>
.quit_button {
    width: 100px;
    height: 30px;
    padding: 5px;
    background: #e54444;
    border-radius: 8px;
    overflow: hidden;
    border: 1px #e54444 solid;
    justify-content: center;
    align-items: center;
    gap: 8px;
    display: inline-flex
}
.quit_button:hover {
    background: #e12222;
    cursor: pointer;
}

.quit_button_text {
    color: #F5F5F5;
    font-size: 13px;
    font-weight: 400;
    line-height: 16px;
    word-wrap: break-word
}

</style>