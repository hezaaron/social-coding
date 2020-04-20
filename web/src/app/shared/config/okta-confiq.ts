import { environment } from '../../../environments/environment';

const redirectUri = `${environment.redirectUri}`;

export const oktaConfig = {
    issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
    clientId: '0oaqs9htbkKF1AOi00h7',
    redirectUri: `${redirectUri}/callback`,
    scopes: ['openid', 'profile'],
    pkce: true
}