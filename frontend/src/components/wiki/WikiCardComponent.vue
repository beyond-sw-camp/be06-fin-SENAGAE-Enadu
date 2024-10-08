<template>
  <div class="wiki-card">
    <router-link :to="computedLink" class="wiki-card-link">
      <div class="wiki-card-image-wrapper">
        <img v-if="wikiCard.thumbnail" :src="wikiCard.thumbnail" alt="Wiki Thumbnail" class="wiki-card-image" />
        <img v-else src="/path-to-default-thumbnail.png" alt="Default Thumbnail" class="wiki-card-image" />
      </div>
      <div class="wiki-card-content">
        <h3 class="wiki-card-title">"{{ wikiCard.title }}"</h3>
        <p class="wiki-card-description">{{ truncatedContent }}</p>
        <div class="wiki-card-category">
          {{ wikiCard.category }}
          <div class="wiki-link-underline"></div>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "WikiCardComponent",
  props: {
    wikiCard: {
      type: Object,
      required: true,
    },
  },
  computed: {
    truncatedContent() {
      return this.wikiCard.content.length > 80
        ? this.wikiCard.content.substring(0, 80) + "..."
        : this.wikiCard.content;
    },
    computedLink() {
      if (this.$route.path.includes("/mypage")) {
        return `/wiki/version/detail?id=${this.wikiCard.id}`;
      }
      return `/wiki/detail?id=${this.wikiCard.id}`;
    },
  },
};
</script>

<style scoped>
.wiki-card {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 270px;
  min-width: 200px;
  height: 394px;
  border-radius: 20px;
  background-color: white;
  box-shadow: 2px 2px 3px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  transition: box-shadow 0.3s ease;
  margin-bottom: 30px;
}

.wiki-card-link {
  text-decoration: none;
  color: inherit;
  display: block;
  height: 100%;
}

.wiki-card:hover {
  box-shadow:  2px 2px 8px rgba(0, 0, 0, 0.2);
}

.wiki-card-image-wrapper {
  height: 50%;
  background-color: #f0f0f0;
}

.wiki-card-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.wiki-card-content {
  padding: 15px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  height: 50%;
  position: relative;
}

.wiki-card-title {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.wiki-card-description {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 15px;
  line-height: 1.4;
  flex-grow: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.wiki-card-category {
  font-size: 1rem;
  color: #999;
  text-align: right;
  margin-top: auto;
  display: inline-block;
  align-self: flex-end;
}

.wiki-card .wiki-link-underline {
  width: 0;
  height: 1px;
  background-color: #999;
  transition: .3s ease-out;
}

.wiki-card:hover .wiki-link-underline {
  width: 100%;
}
</style>
