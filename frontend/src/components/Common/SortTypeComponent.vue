<template>
  <div class="radio-input"
       :style="isAccuracyVisible ? '--container_width: 240px;' : '--container_width: 160px;'">
    <label>
      <input value="latest" name="value-radio" id="value-1" type="radio" v-model="selectedSort"
             @change="emitSortChange('checkLatest')"/>
      <span>최신 순</span>
    </label>
    <label>
      <input value="like" name="value-radio" id="value-2" type="radio" v-model="selectedSort"
             @change="emitSortChange('checkLike')"/>
      <span>좋아요 순</span>
    </label>
    <label v-if="isAccuracyVisible" :class="!$route. path.startsWith('/wiki') ? 'separator' : ''">
      <input value="accuracy" name="value-radio" id="value-3" type="radio" v-model="selectedSort"
             @change="emitSortChange('checkAccuracy')"/>
      <span>정확도 순</span>
    </label>
    <span class="selection" :class="isAccuracyVisible ? 'accuracy' : '' "
          :style="selectionTransform"></span>
  </div>
</template>

<script>
import { mapStores } from "pinia";
import { useQnaStore } from "@/store/useQnaStore";

export default {
  name: "SortTypeComponent",
  data() {
    return {
      selectedSort: null,
      isAccuracyVisible: false
    };
  },
  props: {
    isSearched: Boolean,
  },
  created() {
    if (this.$route.path.startsWith('/errorarchive') && this.$route.query.keyword !== undefined && this.$route.query.keyword.length > 0) {
      this.isAccuracyVisible = true;
    } else if (this.$route.path.startsWith('/qna') && this.isSearched) {
      this.isAccuracyVisible = true;
    }
  },
  mounted() {
    this.selectedSort = "latest";
  },
  computed: {
    ...mapStores(useQnaStore),
  },
  methods: {
    emitSortChange(sortType) {
      this.$emit(sortType);
    },
  },
  watch: {
    isSearched() {
      this.isAccuracyVisible = this.isSearched;
    }
  }
};
</script>

<style scoped>
.radio-input input {
  display: none;
}

.radio-input {
  position: relative;
  display: flex;
  align-items: center;
  border-radius: 10px;
  background-color: #fff;
  color: #000000;
  width: var(--container_width);
  overflow: hidden;
  border: 2px solid rgba(53, 52, 52, 0.226);
  margin-bottom: 20px;
}

.radio-input label {
  width: 100%;
  padding: 10px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1;
  font-weight: 800;
  letter-spacing: -1px;
  font-size: 14px;
}

.selection {
  display: none;
  position: absolute;
  height: 100%;
  width: calc(var(--container_width) / 2);
  z-index: 0;
  left: 0;
  top: 0;
  transition: 0.15s ease;
}

.selection.accuracy {
  width: calc(var(--container_width) / 3);
}

.radio-input label:has(input:checked) {
  color: #fff;
}

.radio-input label:has(input:checked) ~ .selection {
  background-color: var(--main-color);
  display: inline-block;
}

.radio-input label:nth-child(1):has(input:checked) ~ .selection {
  transform: translateX(0);
}

.radio-input label:nth-child(2):has(input:checked) ~ .selection {
  transform: translateX(calc(var(--container_width) * 1 / 2));
}

.radio-input label:nth-child(2):has(input:checked) ~ .selection.accuracy {
  transform: translateX(calc(var(--container_width) * 1 / 3));
}

.radio-input label:nth-child(3):has(input:checked) ~ .selection {
  transform: translateX(calc(var(--container_width) * 2 / 3));
}

</style>