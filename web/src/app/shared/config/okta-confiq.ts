import { environment } from '../../../environments/environment';

const redirectUri = `${environment.redirectUri}`;

export const oktaConfig = {
    issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
    clientId: '0oaj268wh6uRIKLy50h7',
    redirectUri: `${redirectUri}/implicit/callback`,
    scopes: ['openid', 'profile', 'email']
}