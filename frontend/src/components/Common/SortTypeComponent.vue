<template>
  <div class="radio-input">
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
    <span class="selection"></span>
  </div>
</template>

<script>
import {mapStores} from "pinia";
import {useQnaStore} from "@/store/useQnaStore";

export default {
  name: "SortTypeComponent",
  data() {
    return {
      selectedSort: null
    };
  },
  computed: {
    ...mapStores(useQnaStore),
  },
  mounted() {
    this.selectedSort="latest"
  },
  methods: {
    emitSortChange(sortType) {
      this.$emit(sortType);
    },
  },
  components: {
  },
};

</script>

<style>
.radio-input input {
  display: none;
}

.radio-input {
  --container_width: 160px;
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

.radio-input label:has(input:checked) {
  color: #fff;
}

.radio-input label:has(input:checked) ~ .selection {
  background-color: rgb(11 117 223);
  display: inline-block;
}

.radio-input label:nth-child(1):has(input:checked) ~ .selection {
  transform: translateX(calc(var(--container_width) * 0 / 2));
}

.radio-input label:nth-child(2):has(input:checked) ~ .selection {
  transform: translateX(calc(var(--container_width) * 1 / 2));
}

</style>