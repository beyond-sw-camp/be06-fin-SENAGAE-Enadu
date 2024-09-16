<template>
    <div class="password-modify-modal-body">
        <div class="modal-background">
            <div class="modal">
                <div class="modal-header">비밀번호 변경</div>
                <div class="modal-container">
                    <input type="password" class="password-box" v-model="passwordData.oldPassword" placeholder="원래 비밀번호를 입력하세요." />
                    <input type="password" class="password-box" v-model="passwordData.newPassword" placeholder="새 비밀번호를 입력하세요." />
                    <div class="password-description">
                        영문 소문자, 숫자, 특수문자 포함 8자 이상
                    </div>

                    <input type="password" class="password-box" v-model="passwordData.confirmPassword" placeholder="새 비밀번호를 다시 한번 입력하세요." />
                </div>
                <div class="button-group">
                    <button class="btn close-btn" @click="closeModal">닫기</button>
                    <button class="btn check-btn" @click="submitPasswordChange">확인</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapStores} from "pinia";
import {useUserStore} from "@/store/useUserStore";

export default {
    name: "PasswordModal",
    data() {
        return {
            passwordData: {
                oldPassword: "",
                newPassword: "",
                confirmPassword: "",
            }
        }
    },
    computed: {
        ...mapStores(useUserStore)
    },
    methods: {
        closeModal() {
            this.$emit("close");
        },
        submitPasswordChange() {
            if (this.passwordData.newPassword !== this.passwordData.confirmPassword) {
                alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                return;
            }
            this.userStore.updatePassword(this.passwordData)
                .then(() => {
                    alert("비밀번호가 성공적으로 변경되었습니다.");
                    this.closeModal();
                })
                .catch(error => {
                    alert("비밀번호 변경 중 오류가 발생했습니다.");
                    console.error(error);
                });
        }
    }
}
</script>

<style scoped>
.password-modify-modal-body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f0f0f0;
}

/* 흐린 배경 */
.modal-background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

/* 모달창 스타일 */
.modal {
    background-color: white;
    padding: 30px;
    border-radius: 16px;
    width: 400px;
    max-height: 80%;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    overflow: hidden;
}

/* 모달 헤더 */
.modal-header {
    font-size: 24px;
    margin-bottom: 20px;
    color: #333;
    font-weight: bold;
}

/* 비밀번호 입력창 */
.password-box {
    width: 100%;
    padding: 12px 16px;
    border: 1px solid #ddd;
    border-radius: 10px;
    margin-bottom: 40px;
    font-size: 16px;
    box-sizing: border-box;
}

.password-description {
    margin-top: -40px;
    margin-bottom: 30px;
    margin-left: 10px;
    color: rgb(233, 149, 15);
    font-size: 10px;
}

/* 버튼 스타일 */
.button-group {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}

.btn {
    padding: 10px 20px;
    border-radius: 8px;
    border: none;
    cursor: pointer;
    font-size: 14px;
    font-weight: bold;
    transition: background-color 0.3s, transform 0.2s;
}

.btn:focus {
    outline: none;
}

.close-btn {
    background-color: #ff5c5c;
    color: white;
}

.close-btn:hover {
    background-color: #e44e4e;
}

.check-btn {
    background-color: #67adfd;
    color: white;
}

.check-btn:hover {
    background-color: #4e8ce4;
}
</style>