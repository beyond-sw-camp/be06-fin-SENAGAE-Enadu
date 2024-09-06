<template>
            <main class="mx-auto mt-2 w-full max-w-7xl px-4 lg:mt-[18px] lg:px-0">
                <div class="flex lg:space-x-10">
                    <div class="w-full min-w-0 flex-auto lg:static lg:max-h-full lg:overflow-visible">
                        <div class="space-y-10">
                            <div class="space-y-2">
                                <h3 class="text-xl font-medium sm:text-3xl sm:leading-10">에러 아카이브 작성하기
                                </h3>
                            </div>
                            <form>
                                <div class="space-y-12 sm:space-y-14">
                                    <div class="grid grid-cols-1 gap-y-7">
                                        <div class="space-y-1"><label for="title"
                                                class="text-sm font-medium text-gray-700">제목</label><input type="text"
                                                id="title" placeholder="제목을 입력해주세요."
                                                class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                name="title"></div>
                                        <div class="space-y-1"><label for="tags"
                                                class="text-sm font-medium text-gray-700 dark:text-gray-300">카테고리</label>
                                            <div class="flex w-full">
                                                <input type="text" id="title" placeholder="상위 카테고리를 선택해주세요." disabled
                                                    class="w-full  appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                    name="title">
                                                <div style="margin-right:20px">
                                                    <button type="button"
                                                        class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600">
                                                        검색
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="space-y-1"><label for="tags"
                                            class="text-sm font-medium text-gray-700 dark:text-gray-300">카테고리</label>
                                        <div class="flex w-full">
                                            <input type="text" id="title" placeholder="하위 카테고리를 선택해주세요." disabled
                                                class="w-full  appearance-none rounded-md border border-gray-500/30 pl-3 pr-10 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                name="title">
                                            <div style="margin-right:20px">
                                                <button type="button"
                                                    class="w-20 items-center space-x-2 rounded-md bg-blue-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600">
                                                    검색
                                                </button>
                                            </div>
                                        </div>
                                    </div>
    
                                        <!-- 썸네일 등록 -->
                                        <div class="space-y-1">
                                            <label for="thumbnail"
                                                class="text-sm font-medium text-gray-700 dark:text-gray-300">썸네일 등록</label>
                                            <div class="flex items-center space-x-4">
                                                <input type="file" id="thumbnail" name="thumbnail"
                                                    class="block w-full appearance-none rounded-md border border-gray-500/30 pl-3 pr-3 py-2 text-base placeholder-gray-500/80 shadow-sm focus:border-gray-500 focus:outline-none focus:ring-0 dark:bg-gray-500/20"
                                                    accept="image/*">
                                                <button type="button" id="clearThumbnail"
                                                    class="w-20 items-center space-x-2 rounded-md bg-red-500 px-4 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600">
                                                    취소
                                                </button>
                                            </div>
                                        </div>
    
                                        <div class="space-y-1"><label for="text"
                                                class="text-sm font-medium text-gray-700 dark:text-gray-300">본문</label>
                                            <div
                                                class="remirror-theme relative z-0 rounded-md border border-gray-500/30 shadow-sm dark:border-gray-500/70">
                                                <div
                                                    class="relative z-10 block items-center rounded-t-md border-b border-gray-500/30 bg-gray-50 p-1 py-0.5 md:flex dark:border-gray-500/70 dark:bg-gray-900/50">
                                                    <nav class="relative inline-flex space-x-1">
                                                        <button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button">
                                                            <span class="sr-only">header 1</span>
                                                            <svg stroke="currentColor" fill="currentColor" stroke-width="0"
                                                                name="h1" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0H24V24H0z"></path>
                                                                <path
                                                                    d="M13 20h-2v-7H4v7H2V4h2v7h7V4h2v16zm8-12v12h-2v-9.796l-2 .536V8.67L19.5 8H21z">
                                                                </path>
                                                            </svg>
                                                        </button>
                                                        <button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"><span class="sr-only">header 2</span><svg
                                                                stroke="currentColor" fill="currentColor" stroke-width="0"
                                                                name="h2" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0H24V24H0z"></path>
                                                                <path
                                                                    d="M4 4v7h7V4h2v16h-2v-7H4v7H2V4h2zm14.5 4c2.071 0 3.75 1.679 3.75 3.75 0 .857-.288 1.648-.772 2.28l-.148.18L18.034 18H22v2h-7v-1.556l4.82-5.546c.268-.307.43-.709.43-1.148 0-.966-.784-1.75-1.75-1.75-.918 0-1.671.707-1.744 1.606l-.006.144h-2C14.75 9.679 16.429 8 18.5 8z">
                                                                </path>
                                                            </svg></button><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"><span class="sr-only">header 3</span><svg
                                                                stroke="currentColor" fill="currentColor" stroke-width="0"
                                                                name="h3" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0H24V24H0z"></path>
                                                                <path
                                                                    d="M22 8l-.002 2-2.505 2.883c1.59.435 2.757 1.89 2.757 3.617 0 2.071-1.679 3.75-3.75 3.75-1.826 0-3.347-1.305-3.682-3.033l1.964-.382c.156.806.866 1.415 1.718 1.415.966 0 1.75-.784 1.75-1.75s-.784-1.75-1.75-1.75c-.286 0-.556.069-.794.19l-1.307-1.547L19.35 10H15V8h7zM4 4v7h7V4h2v16h-2v-7H4v7H2V4h2z">
                                                                </path>
                                                            </svg></button>
                                                    </nav>
                                                    <div class="mx-2 inline-flex h-4 w-[1px] bg-gray-300"></div>
                                                    <nav class="relative z-0 inline-flex space-x-1 rounded-md"><button
                                                            tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"><span class="sr-only">Bold</span><svg
                                                                stroke="currentColor" fill="currentColor" stroke-width="0"
                                                                name="bold" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M8 11h4.5a2.5 2.5 0 1 0 0-5H8v5zm10 4.5a4.5 4.5 0 0 1-4.5 4.5H6V4h6.5a4.5 4.5 0 0 1 3.256 7.606A4.498 4.498 0 0 1 18 15.5zM8 13v5h5.5a2.5 2.5 0 1 0 0-5H8z">
                                                                </path>
                                                            </svg></button><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"><svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="italic" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M15 20H7v-2h2.927l2.116-12H9V4h8v2h-2.927l-2.116 12H15z">
                                                                </path>
                                                            </svg></button><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"><svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="underline" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M8 3v9a4 4 0 1 0 8 0V3h2v9a6 6 0 1 1-12 0V3h2zM4 20h16v2H4v-2z">
                                                                </path>
                                                            </svg></button><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"> <svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="strikethrough" height="1em"
                                                                width="1em" xmlns="http://www.w3.org/2000/svg"
                                                                viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M17.154 14c.23.516.346 1.09.346 1.72 0 1.342-.524 2.392-1.571 3.147C14.88 19.622 13.433 20 11.586 20c-1.64 0-3.263-.381-4.87-1.144V16.6c1.52.877 3.075 1.316 4.666 1.316 2.551 0 3.83-.732 3.839-2.197a2.21 2.21 0 0 0-.648-1.603l-.12-.117H3v-2h18v2h-3.846zm-4.078-3H7.629a4.086 4.086 0 0 1-.481-.522C6.716 9.92 6.5 9.246 6.5 8.452c0-1.236.466-2.287 1.397-3.153C8.83 4.433 10.271 4 12.222 4c1.471 0 2.879.328 4.222.984v2.152c-1.2-.687-2.515-1.03-3.946-1.03-2.48 0-3.719.782-3.719 2.346 0 .42.218.786.654 1.099.436.313.974.562 1.613.75.62.18 1.297.414 2.03.699z">
                                                                </path>
                                                            </svg></button></nav>
                                                    <div class="mx-2 inline-flex h-4 w-[1px] bg-gray-300"></div>
                                                    <nav class="relative inline-flex space-x-1"><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"><span class="sr-only">toggle bullet
                                                                list</span><svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="listUnordered" height="1em"
                                                                width="1em" xmlns="http://www.w3.org/2000/svg"
                                                                viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M8 4h13v2H8V4zM4.5 6.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 7a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 6.9a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zM8 11h13v2H8v-2zm0 7h13v2H8v-2z">
                                                                </path>
                                                            </svg></button><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"><span class="sr-only">toggle ordered
                                                                list</span><svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="listOrdered" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M8 4h13v2H8V4zM5 3v3h1v1H3V6h1V4H3V3h2zM3 14v-2.5h2V11H3v-1h3v2.5H4v.5h2v1H3zm2 5.5H3v-1h2V18H3v-1h3v4H3v-1h2v-.5zM8 11h13v2H8v-2zm0 7h13v2H8v-2z">
                                                                </path>
                                                            </svg></button></nav>
                                                    <div class="mx-2 inline-flex h-4 w-[1px] bg-gray-300"></div>
                                                    <nav class="relative z-0 inline-flex space-x-1 rounded-md"><button
                                                            tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"> <svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="doubleQuotesR" height="1em"
                                                                width="1em" xmlns="http://www.w3.org/2000/svg"
                                                                viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M19.417 6.679C20.447 7.773 21 9 21 10.989c0 3.5-2.457 6.637-6.03 8.188l-.893-1.378c3.335-1.804 3.987-4.145 4.247-5.621-.537.278-1.24.375-1.929.311-1.804-.167-3.226-1.648-3.226-3.489a3.5 3.5 0 0 1 3.5-3.5c1.073 0 2.099.49 2.748 1.179zm-10 0C10.447 7.773 11 9 11 10.989c0 3.5-2.457 6.637-6.03 8.188l-.893-1.378c3.335-1.804 3.987-4.145 4.247-5.621-.537.278-1.24.375-1.929.311C4.591 12.322 3.17 10.841 3.17 9a3.5 3.5 0 0 1 3.5-3.5c1.073 0 2.099.49 2.748 1.179z">
                                                                </path>
                                                            </svg></button><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"> <svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="codeLine" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M23 12l-7.071 7.071-1.414-1.414L20.172 12l-5.657-5.657 1.414-1.414L23 12zM3.828 12l5.657 5.657-1.414 1.414L1 12l7.071-7.071 1.414 1.414L3.828 12z">
                                                                </path>
                                                            </svg></button><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"> <svg stroke="currentColor" fill="currentColor"
                                                                stroke-width="0" name="codeView" height="1em" width="1em"
                                                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                                                <path fill="none" d="M0 0h24v24H0z"></path>
                                                                <path
                                                                    d="M16.95 8.464l1.414-1.414 4.95 4.95-4.95 4.95-1.414-1.414L20.485 12 16.95 8.464zm-9.9 0L3.515 12l3.535 3.536-1.414 1.414L.686 12l4.95-4.95L7.05 8.464z">
                                                                </path>
                                                            </svg></button>
                                                        <div class="relative" data-headlessui-state=""><button tabindex="-1"
                                                                class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                                type="button" aria-expanded="false" data-headlessui-state=""
                                                                id="headlessui-popover-button-:r14:"><svg
                                                                    xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                    viewBox="0 0 24 24" stroke-width="1.5"
                                                                    stroke="currentColor" aria-hidden="true"
                                                                    data-slot="icon" class="h-4 w-4">
                                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                                        d="M13.19 8.688a4.5 4.5 0 0 1 1.242 7.244l-4.5 4.5a4.5 4.5 0 0 1-6.364-6.364l1.757-1.757m13.35-.622 1.757-1.757a4.5 4.5 0 0 0-6.364-6.364l-4.5 4.5a4.5 4.5 0 0 0 1.242 7.244">
                                                                    </path>
                                                                </svg></button></div>
                                                        <div hidden=""
                                                            style="position: fixed; top: 1px; left: 1px; width: 1px; height: 0px; padding: 0px; margin: -1px; overflow: hidden; clip: rect(0px, 0px, 0px, 0px); white-space: nowrap; border-width: 0px; display: none;">
                                                        </div>
                                                        <div class="relative" data-headlessui-state=""><button tabindex="-1"
                                                                class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                                type="button" aria-expanded="false" data-headlessui-state=""
                                                                id="headlessui-popover-button-:r16:"><svg
                                                                    xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                    viewBox="0 0 24 24" stroke-width="1.5"
                                                                    stroke="currentColor" aria-hidden="true"
                                                                    data-slot="icon" class="h-4 w-4">
                                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                                        d="m2.25 15.75 5.159-5.159a2.25 2.25 0 0 1 3.182 0l5.159 5.159m-1.5-1.5 1.409-1.409a2.25 2.25 0 0 1 3.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 0 0 1.5-1.5V6a1.5 1.5 0 0 0-1.5-1.5H3.75A1.5 1.5 0 0 0 2.25 6v12a1.5 1.5 0 0 0 1.5 1.5Zm10.5-11.25h.008v.008h-.008V8.25Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z">
                                                                    </path>
                                                                </svg></button></div>
                                                        <div hidden=""
                                                            style="position: fixed; top: 1px; left: 1px; width: 1px; height: 0px; padding: 0px; margin: -1px; overflow: hidden; clip: rect(0px, 0px, 0px, 0px); white-space: nowrap; border-width: 0px; display: none;">
                                                        </div>
                                                        <div class="relative" data-headlessui-state=""><button tabindex="-1"
                                                                class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                                type="button" aria-expanded="false" data-headlessui-state=""
                                                                id="headlessui-popover-button-:r18:"><svg
                                                                    xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                    viewBox="0 0 24 24" stroke-width="1.5"
                                                                    stroke="currentColor" aria-hidden="true"
                                                                    data-slot="icon" class="h-4 w-4">
                                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                                        d="M3.375 19.5h17.25m-17.25 0a1.125 1.125 0 0 1-1.125-1.125M3.375 19.5h1.5C5.496 19.5 6 18.996 6 18.375m-3.75 0V5.625m0 12.75v-1.5c0-.621.504-1.125 1.125-1.125m18.375 2.625V5.625m0 12.75c0 .621-.504 1.125-1.125 1.125m1.125-1.125v-1.5c0-.621-.504-1.125-1.125-1.125m0 3.75h-1.5A1.125 1.125 0 0 1 18 18.375M20.625 4.5H3.375m17.25 0c.621 0 1.125.504 1.125 1.125M20.625 4.5h-1.5C18.504 4.5 18 5.004 18 5.625m3.75 0v1.5c0 .621-.504 1.125-1.125 1.125M3.375 4.5c-.621 0-1.125.504-1.125 1.125M3.375 4.5h1.5C5.496 4.5 6 5.004 6 5.625m-3.75 0v1.5c0 .621.504 1.125 1.125 1.125m0 0h1.5m-1.5 0c-.621 0-1.125.504-1.125 1.125v1.5c0 .621.504 1.125 1.125 1.125m1.5-3.75C5.496 8.25 6 7.746 6 7.125v-1.5M4.875 8.25C5.496 8.25 6 8.754 6 9.375v1.5m0-5.25v5.25m0-5.25C6 5.004 6.504 4.5 7.125 4.5h9.75c.621 0 1.125.504 1.125 1.125m1.125 2.625h1.5m-1.5 0A1.125 1.125 0 0 1 18 7.125v-1.5m1.125 2.625c-.621 0-1.125.504-1.125 1.125v1.5m2.625-2.625c.621 0 1.125.504 1.125 1.125v1.5c0 .621-.504 1.125-1.125 1.125M18 5.625v5.25M7.125 12h9.75m-9.75 0A1.125 1.125 0 0 1 6 10.875M7.125 12C6.504 12 6 12.504 6 13.125m0-2.25C6 11.496 5.496 12 4.875 12M18 10.875c0 .621-.504 1.125-1.125 1.125M18 10.875c0 .621.504 1.125 1.125 1.125m-2.25 0c.621 0 1.125.504 1.125 1.125m-12 5.25v-5.25m0 5.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125m-12 0v-1.5c0-.621-.504-1.125-1.125-1.125M18 18.375v-5.25m0 5.25v-1.5c0-.621.504-1.125 1.125-1.125M18 13.125v1.5c0 .621.504 1.125 1.125 1.125M18 13.125c0-.621.504-1.125 1.125-1.125M6 13.125v1.5c0 .621-.504 1.125-1.125 1.125M6 13.125C6 12.504 5.496 12 4.875 12m-1.5 0h1.5m-1.5 0c-.621 0-1.125.504-1.125 1.125v1.5c0 .621.504 1.125 1.125 1.125M19.125 12h1.5m0 0c.621 0 1.125.504 1.125 1.125v1.5c0 .621-.504 1.125-1.125 1.125m-17.25 0h1.5m14.25 0h1.5">
                                                                    </path>
                                                                </svg></button></div>
                                                        <div hidden=""
                                                            style="position: fixed; top: 1px; left: 1px; width: 1px; height: 0px; padding: 0px; margin: -1px; overflow: hidden; clip: rect(0px, 0px, 0px, 0px); white-space: nowrap; border-width: 0px; display: none;">
                                                        </div>
                                                        <div class="relative" data-headlessui-state=""><button tabindex="-1"
                                                                class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                                type="button" aria-expanded="false" data-headlessui-state=""
                                                                id="headlessui-popover-button-:r1a:"><svg
                                                                    xmlns="http://www.w3.org/2000/svg" fill="none"
                                                                    viewBox="0 0 24 24" stroke-width="1.5"
                                                                    stroke="currentColor" aria-hidden="true"
                                                                    data-slot="icon" class="h-4 w-4">
                                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                                        d="M3.375 19.5h17.25m-17.25 0a1.125 1.125 0 0 1-1.125-1.125M3.375 19.5h7.5c.621 0 1.125-.504 1.125-1.125m-9.75 0V5.625m0 12.75v-1.5c0-.621.504-1.125 1.125-1.125m18.375 2.625V5.625m0 12.75c0 .621-.504 1.125-1.125 1.125m1.125-1.125v-1.5c0-.621-.504-1.125-1.125-1.125m0 3.75h-7.5A1.125 1.125 0 0 1 12 18.375m9.75-12.75c0-.621-.504-1.125-1.125-1.125H3.375c-.621 0-1.125.504-1.125 1.125m19.5 0v1.5c0 .621-.504 1.125-1.125 1.125M2.25 5.625v1.5c0 .621.504 1.125 1.125 1.125m0 0h17.25m-17.25 0h7.5c.621 0 1.125.504 1.125 1.125M3.375 8.25c-.621 0-1.125.504-1.125 1.125v1.5c0 .621.504 1.125 1.125 1.125m17.25-3.75h-7.5c-.621 0-1.125.504-1.125 1.125m8.625-1.125c.621 0 1.125.504 1.125 1.125v1.5c0 .621-.504 1.125-1.125 1.125m-17.25 0h7.5m-7.5 0c-.621 0-1.125.504-1.125 1.125v1.5c0 .621.504 1.125 1.125 1.125M12 10.875v-1.5m0 1.5c0 .621-.504 1.125-1.125 1.125M12 10.875c0 .621.504 1.125 1.125 1.125m-2.25 0c.621 0 1.125.504 1.125 1.125M13.125 12h7.5m-7.5 0c-.621 0-1.125.504-1.125 1.125M20.625 12c.621 0 1.125.504 1.125 1.125v1.5c0 .621-.504 1.125-1.125 1.125m-17.25 0h7.5M12 14.625v-1.5m0 1.5c0 .621-.504 1.125-1.125 1.125M12 14.625c0 .621.504 1.125 1.125 1.125m-2.25 0c.621 0 1.125.504 1.125 1.125m0 1.5v-1.5m0 0c0-.621.504-1.125 1.125-1.125m0 0h7.5">
                                                                    </path>
                                                                </svg></button></div>
                                                        <div hidden=""
                                                            style="position: fixed; top: 1px; left: 1px; width: 1px; height: 0px; padding: 0px; margin: -1px; overflow: hidden; clip: rect(0px, 0px, 0px, 0px); white-space: nowrap; border-width: 0px; display: none;">
                                                        </div><button tabindex="-1"
                                                            class="hover:bg-gray-200 dark:hover:bg-gray-700 inline-flex items-center rounded-md p-2 text-sm font-medium"
                                                            type="button"> <svg xmlns="http://www.w3.org/2000/svg"
                                                                fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                                                stroke="currentColor" aria-hidden="true" data-slot="icon"
                                                                class="h-4 w-4">
                                                                <path stroke-linecap="round" stroke-linejoin="round"
                                                                    d="M18 12H6"></path>
                                                            </svg></button>
                                                    </nav>
                                                </div>
                                                <div class="min-h-[300px]">
                                                    <div contenteditable="true" role="textbox" aria-multiline="true"
                                                        aria-label="" aria-placeholder="내용을 입력해주세요." translate="no"
                                                        class="ProseMirror remirror-editor remirror-a11y-dark"><span
                                                            data-id="remirror-positioner-widget" role="presentation"
                                                            contenteditable="false" class="ProseMirror-widget"><span
                                                                class="remirror-positioner"
                                                                style="top: -999999px; left: -999999px; width: 0px; height: 0px;"></span></span>
                                                        <p data-placeholder="내용을 입력해주세요." style=""><br
                                                                class="ProseMirror-trailingBreak">
                                                        </p>
                                                    </div>
                                                </div>
                                                <div
                                                    style="position: absolute; pointer-events: none; left: -999999px; top: -999999px; width: 0px; height: 0px;">
                                                </div>
                                                <div
                                                    style="position: absolute; pointer-events: none; left: -999999px; top: -999999px; width: 0px; height: 0px;">
                                                </div>
                                                <div class="remirror-floating-popover"
                                                    style="position: absolute; left: 0px; top: 0px; transform: translate(-999986px, -999962px);">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mt-5 flex justify-between gap-x-3 mb-10">
                                        <button type="button"
                                            class="w-20 rounded-md bg-white px-4 py-2 text-sm font-medium shadow-sm ring-1 ring-gray-500/30 hover:bg-gray-100 focus:outline-none dark:bg-gray-700 dark:ring-gray-500/70 dark:hover:bg-gray-600">
                                            취소
                                        </button>
                                        <div class="relative flex shrink-0 items-center gap-x-3">
                                            <button type="button"
                                                class="inline-flex items-center space-x-2 rounded-md bg-blue-500 px-8 py-2 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-600 disabled:bg-blue-500 disabled:opacity-40"
                                                disabled="">
                                                등록
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <div></div>
                        </div>
                    </div>
                </div>
            </main>
 </template>
    
<script>
export default {
    name: "WikiBoardRegisterComponent",
    data() {
        return {
            text: '', // 본문 내용
        };
    },
    methods: {
        clearThumbnail() {
            // 파일 입력 필드 초기화
            this.$refs.thumbnailInput.value = ''; 
        }
    }
}
</script>
    
<style scoped>

</style>