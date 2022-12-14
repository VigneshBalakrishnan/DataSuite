export const DfxRoutes = {
  Dashboard: '/',
  Jobs: '/discovery/jobs',
  ClassificationModels: '/classification/models',
  ClassificationBuildModels: '/classification/models/build',
  ClassificationPublishModels: '/classification/models/publish',
  SearchEngine: '/searchengine',
  hubLanding: '/',
  cmsLanding: '/cms',
  dmsLanding: '/dms',
  newBankAccount: '/cms/forms/new/bank-account',
  newClientSetup: '/cms/forms/new/client-setup',
  newEntityCreation: '/cms/forms/new/entity-creation',
  // overview: '/overview',
};

export const DfxUrl = {
  // API: 'http://192.168.0.21:8181/privateapi/', // Live API
  LoginAPI: '/dfx/', // Live API
  // LoginTokenAPI: 'http://192.168.0.21:8181/token', // Live API
  SearchAPI:
    '/dfx/odf/ediscovery/', // Live API
    // 'http://192.168.0.42:9200/mcf4_index/' // MockAPI
  ClassificationAPI:
    '/dfx/ce/classificationengine', // Live API
    // 'http://192.168.0.44:3000/classificationengine', // MockAPI
  DiscoveryURL:
    '/dfx/migrationui/', // Live API
    // 'http://192.168.0.44:3001/discoveryengine' // MockAPI
  DashboardAPI:
    '/dfx/odf/ediscovery/', // Live API
    // 'http://192.168.0.42:9200/migration/' // MockAPI
  dmsAPI:
    '/dfx/lf/privateapi/', // Live API
  cmsAPI:
    '/dfx/cms/api/NAVData/', // Live API
  DocuviewerAPI:
    '/api/dls/resource/convertFile',
  dmsDashboardUrl:
  // '/dfx/dms/lfweb/'
    'http://uatenv.datafabricx.com:8090/lfweb/',
  
  ResourceAPI: '/dfx/migrationapi/',
  analyticsAppURL: 'http://localhost:4200/#/',

};

export const ClientBase = {
  eDiscoveryMenu: true,
  myHubMenu: false,
  businessCentralMenu: false,
  dmsMenu: false,
};
