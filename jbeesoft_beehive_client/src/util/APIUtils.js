import {
    ACCESS_TOKEN,
    API_BASE_URL,
    WEATHER_APPID,
    WEATHER_BASE_URL
} from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    });
    
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

export function getCurrentWeather(location) {
    // TODO do poprawy
    return request({
	url: WEATHER_BASE_URL + "forecast?q=" + location + "&units=imperial&type=accurate&" + WEATHER_APPID,
	method: "GET",
    });
}

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
 * ## output:
 * * _date_ : _Array(___notification___)_;
 *
 * ## notification:
 * * id : _String_;
 * * title : _String_;
 * * description : _String_;
 * * date : _String_;
 * * isRealize : _Boolean_;
 * * author: __user__;
 * * users: _Array(___user___)_;
 *
 * ## user:
 * * id : _String_;
 * * username : _String_;
 * * name : _String_;
 * * email : _String_;
 */
export function getNotifications() {
    return request({
        url: API_BASE_URL + "/notification/me",
        method: "GET",
    });
}
/**
 * ## input:
 * * title : _String_;
 * * description : _String_;
 * * date : _String_;
 * * isRealize : _Boolean_;
 * * usersId : _Array(Integer)_;
 */
export function addNotification(input) {
    return request({
        url: API_BASE_URL + "/notification/new",
        method: "POST",
        body: JSON.stringify(input),
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

/**
 * output = {[
 *   userId: <userId>,
 *   username: <username>,
 *   email: <email>,
 *   privileges: [<Privilege>, ...],
 * ], ...}
 *
 * Privilege = {
 *   id: 2,
 *   name: "HIVE_EDITING",
 *   readableName: "Hive actions",
 *   description: "Right to perform actions like honey collecting or feeding",
 * }
 */
export function getContributors(apiaryId) {
    return request({
        url: API_BASE_URL + "/privileges/contributors/" + apiaryId,
        method: "GET",
    });
}
/**
 * dict = {
 *   targetUser: <userId>,
 *   privileges: [<ACTION_STRING>, ...],
 *   affectedApiaryId: <apiaryId>,
 * }
 * add user to apiary - grant him some privileges to it
 * remove user from apiary - clear his privileges to it
 * OWNER_PRIVILEGE - privilege to modify privileges of others
 */
export function grantPrivileges(dict) {
    return request({
        url: API_BASE_URL + "/privileges/grant",
        method: "POST",
        body: JSON.stringify(dict),
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

export function editApiary(apiaryData, apiaryId) {
    return request({
        url: API_BASE_URL + '/apiary/modify/' + apiaryId,
        method: 'PUT',
        body: JSON.stringify(apiaryData)
    });
}

export function editHive(hiveData, hiveId) {
    return request({
        url: API_BASE_URL + '/hive/modify/' + hiveId,
        method: 'PUT',
        body: JSON.stringify(hiveData)
    });
}

export function deleteQueenFromHive(hiveId) {
    return request({
        url: API_BASE_URL + '/hive/delete/' + hiveId + '/queen',
        method: 'DELETE'
    });
}

export function deleteHive(hiveId) {
    return request({
        url: API_BASE_URL + '/hive/delete/' + hiveId + '/all',
        method: 'DELETE'
    });
}

export function getAllHoneyTypes() {
    return request({
        url: API_BASE_URL + "/honey/type",
        method: 'GET'
    });
}

export function collectHoney(honeyData, apiaryId) {
    return request({
        url: API_BASE_URL + '/action/honeycollecting/' + apiaryId,
        method: 'POST',
        body: JSON.stringify(honeyData)
    });
}

export function feedBees(feedingData, apiaryId) {
    return request({
        url: API_BASE_URL + '/action/feeding/' + apiaryId,
        method: 'POST',
        body: JSON.stringify(feedingData)
    });
}

export function treatment(treatmentData, apiaryId) {
    return request({
        url: API_BASE_URL + '/action/treatment/' + apiaryId,
        method: 'POST',
        body: JSON.stringify(treatmentData)
    });
}

export function inspection(inspectionData, apiaryId) {
    return request({
        url: API_BASE_URL + '/action/inspection/' + apiaryId,
        method: 'POST',
        body: JSON.stringify(inspectionData)
    });
}

export function changeQueen(queenData, apiaryId) {
    return request({
        url: API_BASE_URL + '/action/queenchanging/' + apiaryId,
        method: 'POST',
        body: JSON.stringify(queenData)
    });
}

export function getActionsHistory(apiaryId) {
    return request({
        url: API_BASE_URL + '/apiary/actions-history/' + apiaryId,
        method: 'GET'
    });
}

export function getActionsHistoryForHive(hiveId) {
    return request({
        url: API_BASE_URL + '/hive/actions-history/' + hiveId,
        method: 'GET'
    });
}

export function getMyPrivileges(apiaryId) {
    return request({
        url: API_BASE_URL + '/privileges/my_privileges/' + apiaryId,
        method: 'GET'
    });
}