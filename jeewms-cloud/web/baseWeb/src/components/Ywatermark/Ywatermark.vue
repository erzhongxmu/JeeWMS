<template>
    <div class="make">
        <div class="list">
            <div class="item" v-for="i in 500">
                <span>{{info}}</span>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        name: "watermark",
        props: {
            info: {
                type: String,
                default: '全局水印'
            }
        },
        data() {
            return {
            };
        }
    }
</script>
<style lang="less">
    .make {
        position: fixed;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        z-index: 9999;
        background: rgba(0, 0, 0, 0);
        pointer-events: none;
        .list {
            width: 250%;
            height: 450%;
            position: absolute;
            top: -50%;
            left: -50%;
            transform: rotate(-45deg);
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            pointer-events: none;
            .item {
                font-size: 18px;
                color: rgba(220, 220, 220,1);
                padding: 30px;
                pointer-events: none;
            }
        }
    }
</style>