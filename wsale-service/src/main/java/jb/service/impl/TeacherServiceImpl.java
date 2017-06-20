package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jb.absx.F;
import jb.dao.TeacherDaoI;
import jb.model.Tteacher;
import jb.pageModel.Teacher;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.TeacherServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements TeacherServiceI {

	@Autowired
	private TeacherDaoI teacherDao;

	@Override
	public DataGrid dataGrid(Teacher teacher, PageHelper ph) {
		List<Teacher> ol = new ArrayList<Teacher>();
		String hql = " from Tteacher t ";
		DataGrid dg = dataGridQuery(hql, ph, teacher, teacherDao);
		@SuppressWarnings("unchecked")
		List<Tteacher> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (Tteacher t : l) {
				Teacher o = new Teacher();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(Teacher teacher, Map<String, Object> params) {
		String whereHql = "";	
		if (teacher != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(teacher.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", teacher.getName());
			}		

			if (!F.empty(teacher.getPosition())) {
				whereHql += " and t.position = :position";
				params.put("position", teacher.getPosition());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(Teacher teacher) {
		teacher.setId(jb.absx.UUID.uuid());
		Tteacher t = new Tteacher();
		BeanUtils.copyProperties(teacher, t);
		teacherDao.save(t);
	}

	@Override
	public Teacher get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tteacher t = teacherDao.get("from Tteacher t  where t.id = :id", params);
		Teacher o = new Teacher();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(Teacher teacher) {
		Tteacher t = teacherDao.get(Tteacher.class, teacher.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(teacher, t, new String[] { "id" , "addtime" },true);
		}
	}

	@Override
	public void delete(String id) {
		teacherDao.delete(teacherDao.get(Tteacher.class, id));
	}

	@Override
	public List<Teacher> query(Teacher teacher) {
		List<Teacher> ol = new ArrayList<Teacher>();
		String hql = " from Tteacher t ";
		@SuppressWarnings("unchecked")
		List<Tteacher> l = query(hql, teacher, teacherDao);
		if (l != null && l.size() > 0) {
			for (Tteacher t : l) {
				Teacher o = new Teacher();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public Teacher get(Teacher teacher) {
		String hql = " from Tteacher t ";
		@SuppressWarnings("unchecked")
		List<Tteacher> l = query(hql, teacher, teacherDao);
		Teacher o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new Teacher();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
