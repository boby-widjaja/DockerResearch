<template>
    <div class="base-validation-message">
        <i class="fas fa-exclamation-circle"></i>
        <span>
            <slot></slot>
        </span>
    </div>
</template>

<style scoped>
.base-validation-message{
    margin: 10px 0;
    color: #e74c3c;
    font-weight: bold;
}
</style>