<template>
  <div class="dropdown"  @click.stop="showDropdown">{{ nickname }}
    <ul class="dropdown-menu" v-if="isDropdownVisible">
      <router-link :to="{ path: '/' }"><li><i class="fas fa-user"></i> 회원 정보</li></router-link>
      <li  @click="startChat"><i class="fas fa-comments"></i> 1:1 채팅</li>
    </ul>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import {useChatStore} from "@/store/useChatStore";

export default {
  name: "NicknameComponent",
  props: ['nickname'],
  computed: {
    ...mapStores(useChatStore)
  },
  data() {
    return {
      isDropdownVisible: false,
    }
  },
  methods: {
    showDropdown() {
      this.isDropdownVisible = !this.isDropdownVisible;
    },
    handleClickOutside(event) {
      const dropdownMenu = this.$el.querySelector('.dropdown-menu');
      if (dropdownMenu && !dropdownMenu.contains(event.target)) {
        this.isDropdownVisible = false; // 외부를 클릭하면 드롭다운을 숨김
      }
    },
    async startChat(){
      await this.chatStore.startChat(this.nickname);
      this.$router.push('/chat');
    },
  },
  mounted(){
    document.addEventListener('click', this.handleClickOutside);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside);

  }
}
</script>


<style scoped>
.dropdown {
  position: relative;
  display: inline-block;
  cursor: pointer;
}
.dropdown:hover {
  text-decoration: underline;
}

.dropdown-menu {
  display: block;
  position: absolute;
  background-color: #fff;
  border: 1px solid #ddd;
  z-index: 1000;
  list-style: none;
  width: 113px;
  box-shadow: 1px 1px 5px 0 rgba(0,0,0,0.2);
  font-size: 14px;
  font-weight: normal;
}

.dropdown-menu li {
  padding: 10px 10px;
}
.dropdown-menu li:hover {
  background-color: #f8f8f8;
}

.dropdown-menu li:hover {
  color: var(--main-color);
}

</style>