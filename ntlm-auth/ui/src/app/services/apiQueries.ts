export class APIQueries {
  // Dashboard Queries
  // Total Source count
  public static sourceObj = {
    size: 0,
    aggs: {
      repo_count: {
        cardinality: {
          field: 'location.keyword',
        },
      },
    },
  };
  // Total discovered count
  public static discoveredObj = {};
  // Total Migrated count
  public static migratedObj = {
    query: {
      bool: {
        must: [
          {
            match: {
              migration_status: '1',
            },
          },
        ],
      },
    },
  };
  // Total Document Trained count
  public static trainedObj = {
    size: 0,
    aggs: {
      by_model: {
        cardinality: {
          field: 'clientname.keyword'
        }
      }
    }
  };

  // Top 5 Department Volume
  public static departmentObj = {
    size: 0,
    aggs: {
      by_source: {
        terms: {
          field: 'department.keyword',
          size: 5,
        },
      },
    },
  };
  // Top Data source
  public static dataSourceObj = {
    size: 0,
    aggs: {
      by_source: {
        terms: {
          field: 'location.keyword',
          size: 5,
        },
      },
    },
  };
  public static dataModelObj = {
    size: 0,
    aggs: {
      by_model: {
        terms: {
          field: 'extension.keyword',
          size: 5
        }
      }
    }
  };
  // Trained Document Sets
  public static trainedSetsObj = {
    "size": 0,
    "aggs": {
        "resolve_count": {
            "filters": {
                "filters": {
                    "resolved": {
                        "bool": {
                            "must": [],
                            "filter": [
                                {
                                    "terms": {
                                        "resolve_type": [
                                            1,
                                            3
                                        ]
                                    }
                                }
                            ],
                            "should": [],
                            "must_not": []
                        }
                    },
                    "UnResolved": {
                        "bool": {
                            "must": [],
                            "filter": [
                                {
                                    "bool": {
                                        "should": [
                                            {
                                                "match_phrase": {
                                                    "resolve_type": "0"
                                                }
                                            }
                                        ],
                                        "minimum_should_match": 1
                                    }
                                }
                            ],
                            "should": [],
                            "must_not": []
                        }
                    },
                    "AutoResolved": {
                        "bool": {
                            "must": [],
                            "filter": [
                                {
                                    "bool": {
                                        "should": [
                                            {
                                                "match_phrase": {
                                                    "resolve_type": "3"
                                                }
                                            }
                                        ],
                                        "minimum_should_match": 1
                                    }
                                }
                            ],
                            "should": [],
                            "must_not": []
                        }
                    },
                    "Ignore": {
                        "bool": {
                            "must": [],
                            "filter": [
                                {
                                    "bool": {
                                        "should": [
                                            {
                                                "match_phrase": {
                                                    "resolve_type": "-1"
                                                }
                                            }
                                        ],
                                        "minimum_should_match": 1
                                    }
                                }
                            ],
                            "should": [],
                            "must_not": []
                        }
                    }
                }
            }
        }
    },
    "query": {
        "bool": {
            "must": [],
            "filter": [
                {
                    "match_all": {}
                },
                {
                    "range": {
                        "indexdate": {
                            "format": "strict_date_optional_time",
                            "gte": null,
                            "lte": null
                        }
                    }
                }
            ],
            "should": [],
            "must_not": []
        }
    }
};
  // Zero Kb files
  public static zeroKbFilesObj = {
    query: {
      bool: {
        must: [{ match: { stream_size: 0 } }],
      },
    },
  };
  // Password protected files
  public static passwordProtectedObj = {
    size: 0,
    query: {
      bool: {
        must: [],
        must_not: [
            {
                wildcard: {
                    content: '*'
                }
            }
        ],
      },
    },
    aggs: {
      password: {
        filters: {
          filters: [
            { match: { pdfencrypted: 'true' } },
            { match: { security: '1' } },
            {
              match: {
                'contenttype.keyword': 'application/x-tika-ooxml-protected',
              },
            },
          ],
        },
      },
      total_count_password: {
        sum_bucket: {
          buckets_path: 'password._count',
        },
      },
    },
  };

  // Corrupted file
  public static corruptedFilesObj = {
    "query": {
        "bool": {
            "must_not": [
                {
                    "match": {
                        "stream_size": 0
                    }
                }
            ],
            "filter": [
                {
                    "match_all": {}
                },
                {
                    "range": {
                        "indexdate": {
                            "format": "strict_date_optional_time",
                            "gte": null,
                            "lt": null
                        }
                    }
                }
            ],
            "must": [
                {
                    "match": {
                        "tikaProcessed": "false"
                    }
                }
            ]
        }
    }
};

  // Total Document ignored count
  public static ignoredFilesObj = {
    query: {
      bool: {
        must: [
          {
            match: {
              resolve_type: '-1',
            },
          },
        ],
      },
    },
  };

  // Discovered data for Chart
  public static discoveredChartObj = {
    "size":0,
    "aggs":{
       "groups_by_day":{
          "date_histogram":{
             "field":"indexdate",
             "format":"dd-MM-yyyy",
             "fixed_interval":"1d",
             "min_doc_count":1
          }
       }
    },
    "query":{
       "bool":{
          "must":[
             {
                "range":{
                   "indexdate":{
                      "format":"strict_date_optional_time",
                      "gte":null,
                      "lte":null
                   }
                }
             }
          ]
       }
    }
 };

  // migratedChartObj
  public static migratedChartObj = {
    size: 0,
    query: {
      bool: {
        "must":[
          { match: { migration_status: 1 } },
          {
             "range":{
                "indexdate":{
                   "format":"strict_date_optional_time",
                   "gte":null,
                   "lte":null
                }
             }
          }
       ]
      },
    },
    aggs: {
      groups_by_day: {
        date_histogram: {
          field: 'indexdate',
          format: 'dd-MM-yyyy',
          fixed_interval: '1d',
          min_doc_count: 1,
        },
      },
    },
  };

   // migratedCountObj
   public static migratedCountObj = {
    "size":0,
    "aggs":{
       "migration_count":{
          "filters":{
             "filters":{
                "Migrated":{
                   "bool":{
                      "must":[
                         
                      ],
                      "filter":[
                         {
                            "bool":{
                               "should":[
                                  {
                                     "match_phrase":{
                                        "migration_status":"1"
                                     }
                                  }
                               ],
                               "minimum_should_match":1
                            }
                         }
                      ],
                      "should":[
                         
                      ],
                      "must_not":[
                         
                      ]
                   }
                },
                "NonMigrated":{
                   "bool":{
                      "must":[
                         
                      ],
                      "filter":[
                         {
                            "bool":{
                               "should":[
                                  {
                                     "match_phrase":{
                                        "migration_status":"0"
                                     }
                                  }
                               ],
                               "minimum_should_match":1
                            }
                         }
                      ],
                      "should":[
                         
                      ],
                      "must_not":[
                         
                      ]
                   }
                },
                "MigrationFailed":{
                   "bool":{
                      "must":[
                         
                      ],
                      "filter":[
                         {
                            "bool":{
                               "should":[
                                  {
                                     "match_phrase":{
                                        "migration_status":"-1"
                                     }
                                  }
                               ],
                               "minimum_should_match":1
                            }
                         }
                      ],
                      "should":[
                         
                      ],
                      "must_not":[
                         
                      ]
                   }
                }
             }
          }
       }
    },
    "query":{
       "bool":{
          "must":[
             
          ],
          "filter":[
             {
                "match_all":{
                   
                }
             },
             {
                "match_all":{
                   
                }
             },
             {
                "range":{
                   "indexdate":{
                      "format":"strict_date_optional_time",
                      "gte":null,
                      "lte":null
                   }
                }
             }
          ],
          "should":[
             
          ],
          "must_not":[
             
          ]
       }
    }
 };

}
