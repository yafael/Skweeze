import requests
import json

# resulting csv file
output_file = open('input_data.csv', 'w')

# static params for the API call
base_url = 'https://data.usajobs.gov'
search_url = '/api/Search?'
additional_params = '&PositionSchedule=1&ResultsPerPage=500&Location=United+States'

# codes and titles to be queried
accounting_budget_and_finance_title = 'Accounting Budget and Finance'
accounting_budget_and_finance_codes = 'JobCategoryCode=0510,0525,0511,0560,0561,0530,0501,0503,0570,0505,0599,0545'
biological_sciences_title = 'Biological Sciences'
biological_sciences_codes = 'JobCategoryCode=0471,0487,0404,0408,0414,0460,0462,0401,0440,0437,0459,0403,0413,0434,0455,0454,0457,0470,0415'
business_industry_and_programs_title = 'Business, Industry, and Programs'
business_industry_and_programs_codes = 'JobCategoryCode=1176,1199,1102,1160,1101,1173,1150,1106,1152,1105,1170'
education_title = 'Education'
education_codes = 'JobCategoryCode=1702,1710,1720,1730,1740,1701,1750,1712'
engineering_and_architect_title = 'Engineering And Architect'
engineering_and_architect_codes = 'JobCategoryCode=0861,0890,0808,0858,0893,0810,0854,0809,0850,0855,0856,0899,0802,0819,0804,0801,0896,0807,0806,0830,0880,0871,0840,0881,0803,0817'
food_preparation_and_serving_title = 'Food Preparation And Serving'
food_preparation_and_serving_codes = 'JobCategoryCode=7405,7404,7408,7407,7401,7420'
information_technology_title = 'Information Technology'
information_technology_codes = 'JobCategoryCode=2210,2299'
inspection_investigation_title = 'Inspection, Investigation'
inspection_investigation_codes = 'JobCategoryCode=1825,1896,1802,1862,1811,1895,1881,1863,1801,1810,1822'
management_administrative_and_clerical_services_title = 'Management, Administrative And Clerical Services'
management_administrative_and_clerical_services_codes = 'JobCategoryCode=0399,0341,0394,0335,0334,0356,0392,0306,0304,0346,0305,0343,0344,0302,0301,0303,0326,0340,0318,0342,0391,0382'
medical_dental_and_public_health_title = 'Medical, Dental, And Public Health'
medical_dental_and_public_health_codes = 'JobCategoryCode=0681,0682,0683,0680,0647,0630,0601,0640,0670,0671,0690,0635,0699,0649,0602,0669,0675,0622,0679,0645,0644,0642,0610,0605,0621,0631,0662,0667,0646,0660,0661,0633,0603,0668,0620,0672,0685,0638,0636,0651,0665,0648'
safety_health_and_physical_title = 'Safety, Health, And Physical'
safety_health_and_physical_codes = 'JobCategoryCode=0060,0020,0007,0089,0028,0081,0099,0090,0084,0025,0083,0018,0080,0086,0085,0030'
social_science_psychologist_title = 'Social Science, Psychologist'
social_science_psychologist_codes = 'JobCategoryCode=0193,0110,0190,0150,0170,0132,0180,0181,0189,0188,0101,0102,0199,0186,0185'
supply_title = 'Supply'
supply_codes = 'JobCategoryCode=2001,2010,2091,2005,2003,2099'
transportation_mobile_equipment_maintenance_title = 'Transportation/Mobile Equipment Maintenance'
transportation_mobile_equipment_maintenance_codes = 'JobCategoryCode=5823,5876,5803,5801'

category_dict = {accounting_budget_and_finance_title: accounting_budget_and_finance_codes, biological_sciences_title: biological_sciences_codes,
  business_industry_and_programs_title: business_industry_and_programs_codes, education_title: education_codes,
  engineering_and_architect_title: engineering_and_architect_codes, food_preparation_and_serving_title: food_preparation_and_serving_codes,
  information_technology_title: information_technology_codes, inspection_investigation_title: inspection_investigation_codes,
  management_administrative_and_clerical_services_title: management_administrative_and_clerical_services_codes, 
  medical_dental_and_public_health_title: medical_dental_and_public_health_codes, safety_health_and_physical_title: safety_health_and_physical_codes,
  social_science_psychologist_title: social_science_psychologist_codes, supply_title: supply_codes,
  transportation_mobile_equipment_maintenance_title: transportation_mobile_equipment_maintenance_codes
  }

host = 'data.usajobs.gov'
user_agent = 'loganpatino10@gmail.com'
auth_key = 'FOM/jfQ8/LXcBHKAxyf3fTwIVjAfkgsdsSoEIvlgztI='
headers = {'Host': host,
  'User-Agent': user_agent,
  'Authorization-Key': auth_key}

def get_category_posting_info(category_code_params, category_title):
    print('Fetching job posting info from {}...'.format(category_title))

    current_search_url = base_url + search_url + category_code_params + additional_params
    response = requests.get(current_search_url, headers=headers)
    result = response.json()
    number_of_pages = int(result['SearchResult']['UserArea']['NumberOfPages'])
    result_items = result['SearchResult']['SearchResultItems']
    
    for page in range(1,number_of_pages+1):
        for item in result_items:
            qualifications = item['MatchedObjectDescriptor']['QualificationSummary']
            output_file.write('"{}","{}"\n'.format(qualifications.replace('"', '""'), category_title))
        response = requests.get(current_search_url + '&Page={}'.format(page), headers=headers)
        result = response.json()
        result_items = result['SearchResult']['SearchResultItems']
    
    print('All info fetched!\n')

for title, codes in category_dict.items():
    get_category_posting_info(codes, title)

output_file.close()