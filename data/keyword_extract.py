import json
import csv
from watson_developer_cloud import AlchemyLanguageV1

def extract_keywords(qualification_text):
    alchemy_language = AlchemyLanguageV1(api_key='f36f420fb6c6e1e1179c12162b15decfb7e7631a')
    result = alchemy_language.keywords(text=qualification_text, max_items=40)
    keyword_list = []
    for keyword in result['keywords']:
        keyword_list.append(keyword['text'])
    return keyword_list

with open('rest_of_og_data.csv', 'r') as oldfile:
    with open('newer_data.csv', 'w') as newfile:
        reader = csv.reader(oldfile, delimiter=',')
        writer = csv.writer(newfile, delimiter=',')
        for row in reader:
            text = row[0]
            keyword_list = extract_keywords(text)
            writer.writerow(["{}".format(','.join(keyword_list)), row[1]])