import {defineStore} from 'pinia';
import axios from 'axios';

const backend = "/api";

export const useCommonStore = defineStore('common', {
    state: () => ({
    }),
    actions: {
        async imageUpload(event, insertImage, files){
            try {
                const formData = new FormData();
                formData.append('imgFile', files[0]);

                const response= await axios.post(backend+'/img/upload', formData, {
                    headers: { 'Content-Type': 'multipart/form-data' },
                    withCredentials: true
                });
                insertImage({
                    url:response.data.result,
                    desc: 'desc',
                    width: 'auto',
                    height: 'auto'
                });
            } catch (error) {
                console.error('이미지 업로드 실패:', error);
                return null;  // 업로드 실패 시 null 반환
            }
        }
    }
});


