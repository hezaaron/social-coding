import 'zone.js/dist/zone-node';
import 'reflect-metadata';

import * as mkdirp from 'mkdirp';
import * as path from 'path';
import * as fs from 'fs';

import { ROUTES } from './static.paths';

const { AppServerModuleNgFactory, renderModuleFactory, provideModuleMap, LAZY_MODULE_MAP } = require('./dist/server/main');

const distFolder = path.join(process.cwd(), 'dist', 'browser');
const indexHtml = fs.readFileSync(path.join(distFolder, 'index.html')).toString();

// Run the render process for each of the routes
ROUTES.forEach(route => renderRoute(indexHtml, route));

// This is the function that does the rendering
// and saves the result to the file system
async function renderRoute(document: string, route: string) {
  const html = await renderModuleFactory(
    AppServerModuleNgFactory,
    {
      document,
      url: route,
      extraProviders: [
        provideModuleMap(LAZY_MODULE_MAP)
      ]
    });

  const folder = path.join(distFolder, route);
  mkdirp.sync(folder);
  fs.writeFileSync(path.join(folder, 'index.html'), html);
}