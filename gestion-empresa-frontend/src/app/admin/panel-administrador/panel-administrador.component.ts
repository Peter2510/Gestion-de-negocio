import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { ReportesService } from '../services/reportes/reportes.service';
import * as html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import { saveAs } from 'file-saver';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-panel-administrador',
  templateUrl: './panel-administrador.component.html',
  styleUrls: ['./panel-administrador.component.css']
})
export class PanelAdministradorComponent implements OnInit, AfterViewInit {
  public citasPorSemana: any[] = [];
  public citasPorMes: any[] = [];
  public citasPorAnio: any[] = [];
  
  public fechas: string[] = [];
  public totalCitasSemana: number[] = [];
  public meses: string[] = [];
  public totalCitasMes: number[] = [];
  public anios: number[] = [];
  public totalCitasAnio: number[] = [];

  private chartSemana: Chart | undefined;
  private chartMes: Chart | undefined;
  private chartAnio: Chart | undefined;

  private monthNames: string[] = [
    'Enero', 'Febrero', 'Marzo', 'Abril', 
    'Mayo', 'Junio', 'Julio', 'Agosto', 
    'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
  ];

  constructor(private reportesService: ReportesService) {
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    this.getCitasPorSemana(2024);
    this.getCitasPorMes(2024);
    this.getCitasPorAnio();
  }

  ngAfterViewInit(): void {}

  getCitasPorSemana(anio: number) {
    this.reportesService.getCitasPorSemana(anio).subscribe(data => {
      this.citasPorSemana = data;
      this.totalCitasSemana = this.citasPorSemana.map(item => item.total);

      this.fechas = this.citasPorSemana.map(item => {
        const startDate = this.getStartDateOfWeek(anio, item.semana);
        return startDate.toLocaleDateString();
      });

      this.createChartSemana();
    });
  }

  getCitasPorMes(anio: number) {
    this.reportesService.getCitasPorMes(anio).subscribe(data => {
      this.citasPorMes = data;
      this.meses = this.citasPorMes.map(item => this.monthNames[item.mes - 1]);
      this.totalCitasMes = this.citasPorMes.map(item => item.total);

      this.createChartMes();
    });
  }

  getCitasPorAnio() {
    this.reportesService.getCitasPorAnio().subscribe(data => {
      this.citasPorAnio = data;
      this.anios = this.citasPorAnio.map(item => item.anio);
      this.totalCitasAnio = this.citasPorAnio.map(item => item.total);

      this.createChartAnio();
    });
  }

  private getStartDateOfWeek(year: number, week: number): Date {
    const firstDayOfYear = new Date(year, 0, 1);
    const daysToAdd = (week - 1) * 7;
    const firstDayOfWeek = new Date(firstDayOfYear.setDate(firstDayOfYear.getDate() + daysToAdd));

    const dayOfWeek = firstDayOfWeek.getDay();
    const adjustDays = dayOfWeek === 0 ? 0 : (7 - dayOfWeek);
    firstDayOfWeek.setDate(firstDayOfWeek.getDate() + adjustDays);
    
    return firstDayOfWeek;
  }

  createChartSemana() {
    const ctx = document.getElementById('citasChartSemana') as HTMLCanvasElement;

    if (this.chartSemana) {
      this.chartSemana.destroy();
    }

    this.chartSemana = new Chart(ctx, {
      type: 'bar', 
      data: {
        labels: this.fechas,
        datasets: [{
          label: 'Total Citas por Semana',
          data: this.totalCitasSemana,
          backgroundColor: 'rgba(75, 192, 192, 0.8)', // Color de fondo
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  createChartMes() {
    const ctx = document.getElementById('citasChartMes') as HTMLCanvasElement;

    if (this.chartMes) {
      this.chartMes.destroy();
    }

    this.chartMes = new Chart(ctx, {
      type: 'bar', 
      data: {
        labels: this.meses,
        datasets: [{
          label: 'Total Citas por Mes',
          data: this.totalCitasMes,
          backgroundColor: 'rgba(153, 102, 255, 0.8)', // Color de fondo
          borderColor: 'rgba(153, 102, 255, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  createChartAnio() {
    const ctx = document.getElementById('citasChartAnio') as HTMLCanvasElement;

    if (this.chartAnio) {
      this.chartAnio.destroy();
    }

    this.chartAnio = new Chart(ctx, {
      type: 'bar', 
      data: {
        labels: this.anios,
        datasets: [{
          label: 'Total Citas por Año',
          data: this.totalCitasAnio,
          backgroundColor: 'rgba(255, 159, 64, 0.8)', // Color de fondo
          borderColor: 'rgba(255, 159, 64, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  // Métodos para descargar cada gráfico como imagen
  downloadChartImage(chartId: string, filename: string) {
    const canvas = document.getElementById(chartId) as HTMLCanvasElement;
    const imgData = canvas.toDataURL('image/png');

    // Crear un nuevo canvas con fondo
    const newCanvas = document.createElement('canvas');
    newCanvas.width = canvas.width;
    newCanvas.height = canvas.height;
    const context = newCanvas.getContext('2d');

    // Dibuja el fondo blanco
    if (context) {
      context.fillStyle = 'white';
      context.fillRect(0, 0, newCanvas.width, newCanvas.height);
      context.drawImage(canvas, 0, 0);
    }

    // Convertir a blob y descargar
    newCanvas.toBlob((blob) => {
      if (blob) {
        saveAs(blob, filename);
      }
    });
  }

  downloadImageSemana() {
    this.downloadChartImage('citasChartSemana', 'chart_semanas.png');
  }

  downloadImageMes() {
    this.downloadChartImage('citasChartMes', 'chart_meses.png');
  }

  downloadImageAnio() {
    this.downloadChartImage('citasChartAnio', 'chart_anios.png');
  }

  // Métodos para descargar CSV
  downloadCSV(data: any[], filename: string) {
    const worksheet = XLSX.utils.json_to_sheet(data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Datos');
    
    XLSX.writeFile(workbook, filename);
  }

  downloadCSVSemana() {
    const data = this.citasPorSemana.map((item) => ({
      Semana: item.semana,
      Total: item.total
    }));
    this.downloadCSV(data, 'citas_semana.csv');
  }

  downloadCSVMes() {
    const data = this.citasPorMes.map((item) => ({
      Mes: this.monthNames[item.mes - 1],
      Total: item.total
    }));
    this.downloadCSV(data, 'citas_mes.csv');
  }

  downloadCSVAnio() {
    const data = this.citasPorAnio.map((item) => ({
      Año: item.anio,
      Total: item.total
    }));
    this.downloadCSV(data, 'citas_anio.csv');
  }

  downloadPDF() {
    const pdf = new jsPDF('p', 'mm', 'a4'); // Crear un nuevo PDF en formato A4
    const chartIds = ['citasChartSemana', 'citasChartMes', 'citasChartAnio'];
    const pageWidth = pdf.internal.pageSize.getWidth();
    const pageHeight = pdf.internal.pageSize.getHeight();
    let y = 10; // Y inicial

    chartIds.forEach((chartId, index) => {
      const canvas = document.getElementById(chartId) as HTMLCanvasElement;
      const imgData = canvas.toDataURL('image/png');
      
      // Ajustar el tamaño de la imagen en el PDF
      const imgWidth = pageWidth - 20; // Margen de 10mm a cada lado
      const imgHeight = (canvas.height * imgWidth) / canvas.width; // Mantener la relación de aspecto

      if (y + imgHeight > pageHeight) {
        pdf.addPage(); // Agregar una nueva página si no hay suficiente espacio
        y = 10; // Reiniciar la posición Y
      }

      pdf.addImage(imgData, 'PNG', 10, y, imgWidth, imgHeight); // Añadir imagen al PDF
      y += imgHeight + 10; // Incrementar la posición Y para el siguiente gráfico

      // Añadir título para cada gráfico
      pdf.text(`Gráfico ${index + 1}`, 10, y - imgHeight - 5); // Título encima del gráfico
    });
    
    pdf.save('dashboard_citas.pdf');
}

}
