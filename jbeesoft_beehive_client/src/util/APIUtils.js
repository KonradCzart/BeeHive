import { API_BASE_URL, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response => 
        response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
    );
};

export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/signin",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: API_BASE_URL + "/user/isAvailable/username/" + username,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: API_BASE_URL + "/user/isAvailable/email/" + email,
        method: 'GET'
    });
}


export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getUserProfile(username) {
    return request({
        url: API_BASE_URL + "/users/" + username,
        method: 'GET'
    });
}

export function addApiary(apiaryData) {
    return request({
        url: API_BASE_URL + "/apiary/new",
        method: 'POST',
        body: JSON.stringify(apiaryData)         
    });
}

export function getAllApiaries() {
    return request({
        url: API_BASE_URL + "/apiary/me",
        method: 'GET'
    });
}

export function addHive(apiaryData) {
    return request({
        url: API_BASE_URL + "/hive/new",
        method: 'POST',
        body: JSON.stringify(apiaryData)         
    });
}

/**
 * output = {date-x: [{id, title, description, isRealize, usersId}, ...], ...}
 * date-x = YYYY-MM-DD
 * usersId = [...]
 */
export function getNotifications() {
    return request({
        url: API_BASE_URL + "/notification/me",
        method: "GET",
    });
}
/**
 * dict = {title, description, date, isRealize, usersId}
 */
export function addNotification(dict) {
    return request({
        url: API_BASE_URL + "/notification/new",
        method: "POST",
        body: JSON.stringify(dict),
    });
}

export function realizeNotification(notifId) {
    return request({
        url: API_BASE_URL + "/notification/realize/" + notifId,
        method: "PUT",
    });
}

export function unrealizeNotification(notifId) {
    return request({
        url: API_BASE_URL + "/notification/unrealize/" + notifId,
        method: "PUT",
    });
}

/**
 * output = [{id, value(=username)}, ...]
 */
export function getUsersLike(string) {
    return request({
        url: API_BASE_URL + "/user/like/" + string,
        method: "GET",
    });
}

/**
 *  dict = {id, title?, description?, date?, isRealize?, usersId?}
 */
export function modifyNotification(dict) {
    return request({
        url: API_BASE_URL + "/notification/modify",
        method: "PUT",
        body: JSON.stringify(dict),
    });
}

export function deleteNotification(notifId) {
    return request({
        url: API_BASE_URL + "/notification/delete/" + notifId,
        method: "DELETE",
    });
}

export function getAllHiveTypes() {
    return request({
        url: API_BASE_URL + "/hive/type",
        method: 'GET'
    });
}

export function getAllHives(apiaryId) {
    return request({
        url: API_BASE_URL + "/apiary/" + apiaryId,
        method: 'GET'
    });
}

export function getHiveData(hiveId) {
    return request({
        url: API_BASE_URL + "/hive/" + hiveId,
        method: 'GET'
    })
}

export function getQueenRaces() {
    return request({
        url: API_BASE_URL + "/queen/race",
        method: 'GET'
    });
}

export function addQueenToHive(queenData) {
    return request({
        url: API_BASE_URL + "/queen/new",
        method: 'POST',
        body: JSON.stringify(queenData)         
    });
}

export function editQueenInHive(queenData) {
    return request({
        url: API_BASE_URL + "/queen/modify",
        method: 'PUT',
        body: JSON.stringify(queenData)         
    });
}