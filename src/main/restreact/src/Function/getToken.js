// 저장된 쿠키 값을 가져오는 함수
export default function getToken(name) {
    // 쿠키 이름을 찾기 위해 전체 쿠키 문자열을 가져옵니다.
    const cookieString = document.cookie;

    // 쿠키를 세미콜론으로 분리하여 배열로 만듭니다.
    const cookies = cookieString.split(';');

    // 주어진 이름을 가진 쿠키를 찾습니다.
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        // 이름이 일치하는 쿠키를 찾았다면 해당 값을 반환합니다.
        if (cookie.indexOf(name + '=') === 0) {
            return cookie.substring(name.length + 1, cookie.length);
        }
    }
    // 일치하는 쿠키를 찾지 못한 경우에는 null을 반환합니다.
    return null;
}