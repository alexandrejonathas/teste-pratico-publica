package br.com.publica.testepratico.domain.service;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.publica.testepratico.domain.model.dto.PeriodoRelatorio;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Classe responsável por implementar as regras de négocios referente ao relatórios.
 * @author Jonathas Lima
 * @version 1.0
 * @since Release 01 da aplicação
 */

@Service
public class RelatorioService {

	@Autowired
	private DataSource dataSource;
	
	
	/**
	 * Método responsável por montar um pdf e devolver um array de bytes referente ao arquivo.
	 * @param periodoRelatorio
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] gerarRelatorioPontuacaoPorPeriodo(PeriodoRelatorio periodoRelatorio) throws Exception {
		
		Date dataInicial = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0)).atZone(ZoneId.systemDefault()).toInstant());
		Date dataFinal = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59)).atZone(ZoneId.systemDefault()).toInstant());
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", dataInicial);
		parametros.put("data_fim", dataFinal);
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_pontuacao_periodo.jasper");
		
		Connection conn = this.dataSource.getConnection();
		
		try {
			JasperPrint jp = JasperFillManager.fillReport(inputStream, parametros, conn);
			return JasperExportManager.exportReportToPdf(jp);
		} finally {
			conn.close();
		}
	}
	
}
