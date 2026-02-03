<template>
    <div class="background">
        <div>
            <base-logo></base-logo>
        </div>
        <base-card>
            <router-view></router-view>
        </base-card>
    </div>
</template>

<style scoped>
.background{
    background-color: var(--twinkle);
    width: 100%;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
:deep(div.base-card){
    min-width: 400px;
    padding: 25px;
}
:deep(.title){
    text-align: center;
    margin: 0 0 25px 0;
    text-transform: capitalize;
    font-weight: bold;
    font-size: 18px;
    color:var(--electron);
}
:deep([type="text"]),
:deep([type="password"]){
    display: block;
    width: 100%;
    box-sizing: border-box;
    border:none;
    outline:none;
    border-bottom: solid 1px var(--electron);
    height: 30px;
    margin-bottom: 15px;
    padding: 15px;
}
:deep([type="button"]){
    background: var(--electron);
    color: white;
    cursor: pointer;
    text-transform: uppercase;
    border: none;
    padding: 15px;
    border-radius: 4px;
    margin: 20px 0;
    display: block;
    width: 100%;
}
:deep(.checkbox-container){
    display:flex;
    align-items: center;
    column-gap: 8px;
}
:deep(.link-container){
    text-align: center;
}
:deep(.link-container > a){
    color:var(--electron);
    text-decoration: underline;
    font-size: 14px;
}
:deep(select){
    width: 100%;
    margin: 20px 0;
    height: 35px;
    display: block;
    box-sizing: border-box;
    padding: 5px;
    border-radius: 4px;
    border:solid 1px var(--silver);
}
:deep(.validation-message){
    color:#e74c3c;
    font-weight: bold;
    margin-bottom: 10px;
}
:deep(.validation-message > i){
    margin-right: 4px;
}
</style>