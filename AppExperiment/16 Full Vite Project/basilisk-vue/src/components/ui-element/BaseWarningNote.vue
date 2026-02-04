<template>
    <div class="base-warning-note">
        <div class="title">
            <i class="fas fa-exclamation-triangle"></i>
            <span>Warning</span>
        </div>
        <slot></slot>
    </div>
</template>

<style scoped>
.base-warning-note{
    max-width: 450px;
    background-color: rgba(241, 196, 15, 0.4);
    border-left: solid 8px #cc8e35;
    padding: 15px;
    box-sizing: border-box;
    color:#4b4b4b;
    border-radius: 6px;
    margin: 12px 0;
}
.base-warning-note .title{
    font-size: 14px;
    border-bottom: #4b4b4b 2px solid;
    padding-bottom:10px;
    margin-bottom: 20px;
}
.base-warning-note .title > i{
    margin-right: 6px;
}
.base-warning-note :deep(p){
    margin-bottom: 18px;
    line-height: 18px;
}
.base-warning-note :deep(p:last-child){
    margin-bottom: 0;
}
@media only screen and (max-width:1024px) {
    .base-warning-note{
        max-width: 280px;
    }
}
</style>