import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import piniaPersistedstate from "pinia-plugin-persistedstate";
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';

// highlightjs
import hljs from 'highlight.js';
import koKR from '@kangc/v-md-editor/lib/lang/ko-KR';

VMdEditor.lang.use('ko-KR', koKR);

VMdEditor.use(githubTheme, { Hljs: hljs });
VMdPreview.use(githubTheme, { Hljs: hljs });

const pinia = createPinia();
pinia.use(piniaPersistedstate);
const app = createApp(App);

app.use(pinia);
app.use(router);
app.use(VMdEditor);
app.use(VMdPreview);

app.mount('#app')

