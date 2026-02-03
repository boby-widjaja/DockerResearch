<template>
    <div class="base-header">
        <div class="label">
            <slot name="label"></slot>
        </div>
        <div class="value">
            <slot name="value"></slot>
        </div>
    </div>
</template>

<style scoped>
.base-header{
    display: flex;
    font-size: 14px;
    margin-bottom: 12px;
}
.base-header:last-child{
    margin-bottom: 0;
}
.label{
    width: 100px;
    flex-shrink: 0;
    font-weight: bold;
    color:var(--electron);
}
</style>