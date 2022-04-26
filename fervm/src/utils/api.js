import store from 'store'
/*
The first set of API is the one on the "production" server. You can use these ones to debug the function , the response and many other. 
If you want to Login you can use the following

email: email@livio.it
psw: puppa

if you want a charachter with admin right;

email: email@claudio.it
psw: puppa
*/

// export const API_URL = {
//     'GUILD': 'https://www.fervm.it:8443/fervmAPI/guild',
//     'CHARACTER': 'https://www.fervm.it:8443/fervmAPI/character',
//     'CHAT' : 'https://www.fervm.it:8443/fervmAPI/chat',
//     'PRESENTI': 'https://www.fervm.it:8443/fervmAPI/presenti',
//     'USER': 'https://www.fervm.it:8443/fervmAPI/user',
//     'FORUM': 'https://www.fervm.it:8443/fervmAPI/forum',
//     'SUBFORUM': 'https://www.fervm.it:8443/fervmAPI/subforum',
//     'POST' : 'https://www.fervm.it:8443/fervmAPI/post',
//     'COMMENT': 'https://www.fervm.it:8443/fervmAPI/comment'
// }
export const getJwt = ()=>{
    return store.get("jwt")
}

export const sanitazeHTML = (testo)=>{
    var SCRIPT_REGEX = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi;
        testo = testo.replace(SCRIPT_REGEX, '')
        return testo;
}

export const API_URL = {
    'GUILD': 'http://localhost:8080/guild',
    'CHARACTER': 'http://localhost:8080/character',
    'CHAT' : 'http://localhost:8080/chat',
    'PRESENTI': 'http://localhost:8080/presenti',
    'USER': 'http://localhost:8080/user',
    'FORUM': 'http://localhost:8080/forum',
    'SUBFORUM': 'http://localhost:8080/forum/subforum',
    'POST' : 'http://localhost:8080/forum/post',
    'COMMENT': 'http://localhost:8080/forum/comment',
    'MISSIVE': 'http://localhost:8080/missive'
}