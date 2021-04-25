from distutils.core import setup, Extension
setup(name = 'qconf_py', version = '1.0.0', ext_modules = [Extension('qconf_py', ['lib/python_qconf.cc'],
     include_dirs=['/usr/local/soldier/qconf_agent/include'],
     extra_objects=['/usr/local/soldier/qconf_agent/lib/libqconf.a']
     )])
