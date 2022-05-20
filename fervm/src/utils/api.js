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

export const API_URL = {
    'GUILD': 'https://www.fervm.it:8443/fervmAPI/guild',
    'CHARACTER': 'https://www.fervm.it:8443/fervmAPI/character',
    'CHAT' : 'https://www.fervm.it:8443/fervmAPI/chat',
    'PRESENTI': 'https://www.fervm.it:8443/fervmAPI/presenti',
    'USER': 'https://www.fervm.it:8443/fervmAPI/user',
    'FORUM': 'https://www.fervm.it:8443/fervmAPI/forum',
    'SUBFORUM': 'https://www.fervm.it:8443/fervmAPI/forum/subforum',
    'POST' : 'https://www.fervm.it:8443/fervmAPI/forum/post',
    'COMMENT': 'https://www.fervm.it:8443/fervmAPI/forum/comment',
    'MISSIVE': 'https://www.fervm.it:8443/fervmAPI/missive',
    'JWT': "https://www.fervm.it:8443/fervmAPI/vrbe/isalive"
}
export const getJwt = ()=>{
    return store.get('jwt')
}

export const sanitazeHTML = (testo)=>{
    var SCRIPT_REGEX = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi;
        testo = testo.replace(SCRIPT_REGEX, '')
        return testo;
}

// export const API_URL = {
//     'GUILD': 'http://192.168.1.178:8080/guild',
//     'CHARACTER': 'http://192.168.1.178:8080/character',
//     'CHAT' : 'http://192.168.1.178:8080/chat',
//     'PRESENTI': 'http://192.168.1.178:8080/presenti',
//     'USER': 'http://192.168.1.178:8080/user',
//     'FORUM': 'http://192.168.1.178:8080/forum',
//     'SUBFORUM': 'http://192.168.1.178:8080/forum/subforum',
//     'POST' : 'http://192.168.1.178:8080/forum/post',
//     'COMMENT': 'http://192.168.1.178:8080/forum/comment',
//     'MISSIVE': 'http://192.168.1.178:8080/missive',
//     'JWT': "http://192.168.1.178:8080/vrbe/isalive"
// }