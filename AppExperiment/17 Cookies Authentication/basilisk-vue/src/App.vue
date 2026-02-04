<template>
  <router-view></router-view>
</template>

<style>
:root{
  --mazarine:#273c75;
  --electron:#0984e3;
  --twinkle:#ced6e0;
  --silver:#bdc3c7;
  --dracula:#2d3436;
}
@font-face {
    font-family: 'OpenSans';
    src: url("./assets/fonts/OpenSans-Regular.ttf") format("truetype");
}
html {
    font-family: "OpenSans", "Lucida Sans", "Lucida Sans Regular", "Lucida Grande", "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
    color: var(--mazarine);
    font-size: 12px;
    font-weight: normal;
}
a{
  text-decoration: none;
  color: var(--mazarine);
}
button{
  border:none;
  cursor: pointer;
}
h1,h2,h3,h4,h5,h6,p{
  margin:0;
}
</style>
