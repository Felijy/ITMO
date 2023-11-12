import yaml
from yaml import SafeLoader
import xmltodict

yaml_file = open('in1.yml')
data_dict = yaml.load(yaml_file, Loader=SafeLoader)
out_file = open("res_dop1.xml", "w")
xml_res = xmltodict.unparse(data_dict, output=out_file)
yaml_file.close()
out_file.close()

